/*
 * Copyright (C) 2003-2012 David E. Berry
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * A copy of the GNU Lesser General Public License may also be found at
 * http://www.gnu.org/licenses/lgpl.txt
 */
package org.synchronoss.cpo;

import org.apache.xmlbeans.XmlException;
import org.slf4j.*;
import org.synchronoss.cpo.config.CpoConfigProcessor;
import org.synchronoss.cpo.core.cpoCoreConfig.*;
import org.synchronoss.cpo.helper.*;
import org.synchronoss.cpo.meta.CpoMetaDescriptor;

import java.io.*;
import java.util.*;

/**
 * @author dberry
 */
public final class CpoAdapterFactoryManager {

  private static final Logger logger = LoggerFactory.getLogger(CpoAdapterFactoryManager.class);
  private static final String CPO_CONFIG_XML = "/cpoConfig.xml";
  private static volatile Map<String, CpoAdapterFactory> adapterMap = new HashMap<>();
  private static String defaultContext = null;

  static {
    loadAdapters(CPO_CONFIG_XML);
  }

  public static CpoAdapter getCpoAdapter() throws CpoException {
    return getCpoAdapter(defaultContext);
  }

  public static CpoAdapter getCpoAdapter(String context) throws CpoException {
    CpoAdapter cpoAdapter=null;
    CpoAdapterFactory cpoAdapterFactory = adapterMap.get(context);
    if (cpoAdapterFactory != null) {
      cpoAdapter = cpoAdapterFactory.getCpoAdapter();
    }
    return cpoAdapter;
  }

  public static CpoTrxAdapter getCpoTrxAdapter() throws CpoException {
    return getCpoTrxAdapter(defaultContext);
  }

  public static CpoTrxAdapter getCpoTrxAdapter(String context) throws CpoException {
    CpoTrxAdapter cpoTrxAdapter=null;
    CpoAdapterFactory cpoAdapterFactory = adapterMap.get(context);
    if (cpoAdapterFactory != null) {
      cpoTrxAdapter = cpoAdapterFactory.getCpoTrxAdapter();
    }
    return cpoTrxAdapter;
  }

  public static CpoXaAdapter getCpoXaAdapter() throws CpoException {
    return getCpoXaAdapter(defaultContext);
  }

  public static CpoXaAdapter getCpoXaAdapter(String context) throws CpoException {
    CpoXaAdapter cpoXaAdapter=null;
    CpoAdapterFactory cpoAdapterFactory = adapterMap.get(context);
    if (cpoAdapterFactory != null) {
      cpoXaAdapter = cpoAdapterFactory.getCpoXaAdapter();
    }
    return cpoXaAdapter;
  }


  public static void loadAdapters(String configFile) {


    InputStream is = CpoClassLoader.getResourceAsStream(configFile);
    if (is == null) {
      logger.info("Resource Not Found: " + configFile);
      try {
        is = new FileInputStream(configFile);
      } catch (FileNotFoundException fnfe) {
        logger.info("File Not Found: " + configFile);
        is = null;
      }
    }

    try {
      // We are doing a load clear all the caches first, in case the load gets called more than once.
      CpoMetaDescriptor.clearAllInstances();
      adapterMap.clear();

      CpoConfigDocument configDoc;
      if (is == null) {
        configDoc = CpoConfigDocument.Factory.parse(configFile);
      } else {
        configDoc = CpoConfigDocument.Factory.parse(is);
      }

      String errMsg = XmlBeansHelper.validateXml(configDoc);
      if (errMsg != null) {
        logger.error("Invalid CPO Config file: " + configFile + ":" + errMsg);
      } else {
        CtCpoConfig cpoConfig = configDoc.getCpoConfig();

        // Set the default context.
        if (cpoConfig.isSetDefaultConfig()) {
          defaultContext = cpoConfig.getDefaultConfig();
        } else {
          // make the first listed config the default.
          defaultContext = cpoConfig.getDataConfigArray(0).getName();
        }

        for (CtMetaDescriptor metaDescriptor : cpoConfig.getMetaConfigArray()) {
          boolean caseSensitive = true;
          if (metaDescriptor.isSetCaseSensitive()) {
            caseSensitive = metaDescriptor.getCaseSensitive();
          }

          // this will create and cache, so we don't need the return
          CpoMetaDescriptor.getInstance(metaDescriptor.getName(), metaDescriptor.getMetaXmlArray(), caseSensitive);
        }

        // now lets loop through all the adapters and get them cached.
        for (CtDataSourceConfig dataSourceConfig : cpoConfig.getDataConfigArray()) {
          CpoAdapterFactory cpoAdapterFactory = makeCpoAdapterFactory(dataSourceConfig);
          if (cpoAdapterFactory != null) {
            adapterMap.put(dataSourceConfig.getName(), cpoAdapterFactory);
          }
        }
      }
    } catch (IOException ioe) {
      logger.error("Error reading " + configFile + ": ", ioe);
    } catch (XmlException xe) {
      logger.error("Error processing " + configFile + ": Invalid XML");
    } catch (CpoException ce) {
      logger.error("Error processing " + configFile + ": ", ce);
    }
  }

  public static CpoAdapterFactory makeCpoAdapterFactory(CtDataSourceConfig dataSourceConfig) throws CpoException {
    CpoAdapterFactory cpoAdapterFactory;

    // make the CpoAdapter
    try {
      CpoConfigProcessor configProcessor = (CpoConfigProcessor)CpoClassLoader.forName(dataSourceConfig.getCpoConfigProcessor()).newInstance();
      cpoAdapterFactory = configProcessor.processCpoConfig(dataSourceConfig);
    } catch (ClassNotFoundException cnfe) {
      String msg = "CpoConfigProcessor not found: " + dataSourceConfig.getCpoConfigProcessor();
      logger.error(msg);
      throw new CpoException(msg);
    } catch (IllegalAccessException iae) {
      String msg = "Could not access CpoConfigProcessor: " + dataSourceConfig.getCpoConfigProcessor();
      logger.error(msg);
      throw new CpoException(msg);
    } catch (InstantiationException ie) {
      String msg = "Could not instantiate CpoConfigProcessor: " + dataSourceConfig.getCpoConfigProcessor() + ": " + ExceptionHelper.getLocalizedMessage(ie);
      logger.error(msg);
      throw new CpoException(msg);
    } catch (ClassCastException cce) {
      String msg = "Class is not instance of CpoConfigProcessor: " + dataSourceConfig.getCpoConfigProcessor();
      logger.error(msg);
      throw new CpoException(msg);
    }

    return cpoAdapterFactory;
  }
}
