package org.synchronoss.cpo.transform.jdbc;

import oracle.xdb.XMLType;
import org.slf4j.*;
import org.synchronoss.cpo.CpoException;
import org.synchronoss.cpo.jdbc.*;

import java.sql.*;

/**
 * User: michael
 * Date: 10/7/11
 * Time: 4:03 PM
 */
public class TransformOracleXml implements JdbcCpoTransform<XMLType, String> {

  private static Logger logger = LoggerFactory.getLogger(TransformOracleXml.class.getName());

  public TransformOracleXml() {
  }

  /**
   * Transforms the datasource object into an object required by the class
   *
   * @param xmlType The xml datatype represented as an object
   *
   * @return An XML String to be stored in the attribute of the cpo bean
   *
   * @throws CpoException
   */
  public String transformIn(XMLType xmlType) throws CpoException {
    logger.debug("ENTERING transformIn");
    String xmlString = null;
    try {
      xmlString = xmlType.getStringVal();
      logger.debug("XML string size is: " + xmlString.length());
    } catch (SQLException se) {
      logger.error("Unable to transform oracle XMLType to String", se);
    }
    logger.debug("EXITING transformIn");
    return xmlString;
  }

  @Override
  public XMLType transformOut(String attributeObject) throws CpoException {
    throw new UnsupportedOperationException("org.synchronoss.cpo.transform.jdbc.TransformOracleXml.transformOut has not been implemented.");
  }

  /**
   * Transforms the xmlString from the class attribute to the xml object required by the datasource
   *
   * @param jpsf The JdbcPreparedStatementFactory object that provides access to the PreparedStatement if needed.
   * @param xmlString The xmlString from the cpoBean that will be converted into the oracle
   * object that will be stored into the database.
   *
   * @return The oracle object to be stored in the datasource
   *
   * @throws CpoException
   */
  @Override
  public XMLType transformOut(JdbcPreparedStatementFactory jpsf, String xmlString) throws CpoException {
    XMLType retval = null;
    try {
      Connection conn = jpsf.getPreparedStatement().getConnection();
      retval = XMLType.createXML(conn, xmlString);
    } catch (SQLException se) {
      logger.error("Unable to transform String to oracle XMLType", se);
    }
    return retval;
  }

  /**
   * Transforms the xmlString from the class attribute to the xml object required by the datasource
   *
   * @param jpsf The JdbcCallableStatementFactory object that provides access to the CallableStatement if needed.
   * @param xmlString The xmlString from the cpoBean that will be converted into the oracle
   * object that will be stored into the database.
   *
   * @return The oracle object to be stored in the datasource
   *
   * @throws CpoException
   */
  public XMLType transformOut(JdbcCallableStatementFactory jpsf, String xmlString) throws CpoException {
    XMLType retval = null;
    try {
      Connection conn = jpsf.getCallableStatement().getConnection();
      retval = XMLType.createXML(conn, xmlString);
    } catch (SQLException se) {
      logger.error("Unable to transform String to oracle XMLType", se);
    }
    return retval;
  }
}
