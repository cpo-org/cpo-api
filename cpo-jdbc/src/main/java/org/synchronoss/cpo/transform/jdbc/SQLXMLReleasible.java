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

import org.synchronoss.cpo.*;

import java.sql.SQLXML;

/**
 * Releasible for SQLXML types
 *
 * @author Michael Bellomo
 * @since 11/10/2014
 */
public class SQLXMLReleasible implements CpoReleasible {

  private SQLXML sqlxml = null;

  @SuppressWarnings("unused")
  private SQLXMLReleasible() {
  }

  public SQLXMLReleasible(SQLXML sqlxml) {
    this.sqlxml = sqlxml;
  }

  @Override
  public void release() throws CpoException {
    try {
      if (sqlxml != null) {
        sqlxml.free();
        sqlxml = null;
      }
    } catch (Exception e) {
      throw new CpoException("Error releasing SQLXML", e);
    }
  }
}
