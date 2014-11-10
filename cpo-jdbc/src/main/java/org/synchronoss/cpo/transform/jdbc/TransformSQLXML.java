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
package org.synchronoss.cpo.transform.jdbc;

import org.slf4j.*;
import org.synchronoss.cpo.CpoException;
import org.synchronoss.cpo.jdbc.*;

import java.sql.*;

/**
 * FIXME - add description
 *
 * @author Michael Bellomo
 * @since 11/10/2014
 */
public class TransformSQLXML implements JdbcCpoTransform<SQLXML, String> {

  private static Logger logger = LoggerFactory.getLogger(TransformSQLXML.class.getName());

  public TransformSQLXML() {
  }

  /**
   * Transforms the datasource object into an object required by the class
   *
   * @param xml The xml datatype represented as an object
   * @return An XML String to be stored in the attribute of the cpo bean
   * @throws CpoException
   */
  public String transformIn(SQLXML xml) throws CpoException {

    logger.debug("ENTERING transformIn");
    String xmlString = null;
    try {
      xmlString = xml.getString();
      logger.debug("XML string size is: " + xmlString.length());
    } catch (SQLException se) {
      logger.error("Unable to transform SQLXML to String", se);
    }
    logger.debug("EXITING transformIn");
    return xmlString;
  }

  @Override
  public SQLXML transformOut(String attributeObject) throws CpoException {
    throw new UnsupportedOperationException("org.synchronoss.cpo.transform.jdbc.TransformSQLXML.transformOut has not been implemented.");
  }

  /**
   * Transforms the xmlString from the class attribute to the xml object required by the datasource
   *
   * @param jpsf The JdbcPreparedStatementFactory object that provides access to the PreparedStatement if needed.
   * @param xmlString The xmlString from the cpoBean that will be converted into the xml
   * object that will be stored into the database.
   * @return The xml object to be stored in the datasource
   * @throws CpoException
   */
  @Override
  public SQLXML transformOut(JdbcPreparedStatementFactory jpsf, String xmlString) throws CpoException {
    SQLXML retval = null;
    try {
      Connection conn = jpsf.getPreparedStatement().getConnection();
      retval = conn.createSQLXML();
      jpsf.AddReleasible(new SQLXMLReleasible(retval));

      retval.setString(xmlString);
    } catch (SQLException se) {
      logger.error("Unable to transform String to SQLXML", se);
    }
    return retval;
  }

  /**
   * Transforms the xmlString from the class attribute to the xml object required by the datasource
   *
   * @param jpsf The JdbcCallableStatementFactory object that provides access to the CallableStatement if needed.
   * @param xmlString The xmlString from the cpoBean that will be converted into the xml
   * object that will be stored into the database.
   * @return The xml object to be stored in the datasource
   * @throws CpoException
   */
  public SQLXML transformOut(JdbcCallableStatementFactory jpsf, String xmlString) throws CpoException {
    SQLXML retval = null;
    try {
      Connection conn = jpsf.getCallableStatement().getConnection();
      retval = conn.createSQLXML();
      jpsf.AddReleasible(new SQLXMLReleasible(retval));

      retval.setString(xmlString);
    } catch (SQLException se) {
      logger.error("Unable to transform String to SQLXML", se);
    }
    return retval;
  }
}
