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

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Reader;

/**
 * CpoCharArrayReader is a utility class to help process CharArrayReader
 *
 * @author david berry
 */
public class CpoCharArrayReader extends CharArrayReader implements java.io.Serializable, java.lang.Cloneable {

  /**
   * Version Id for this class.
   */
  private static final long serialVersionUID = 1L;
  private char[] buffer_ = null; //The buffer for the byte Array
  private int offset_ = 0;
  private int size_ = 0;

  public CpoCharArrayReader(char[] buffer) {
    super(buffer);
    buffer_ = buffer;
  }

  public CpoCharArrayReader(char[] buffer, int offset, int length) {
    super(buffer, offset, length);
    buffer_ = buffer;
    offset_ = offset;
    size_ = length;
  }

  protected void setBuffer(char[] buffer) {
    buffer_ = buffer;
  }

  protected char[] getBuffer() {
    return buffer_;
  }

  protected void setOffset(int offset) {
    offset_ = offset;
  }

  protected int getOffset() {
    return offset_;
  }

  protected void setSize(int size) {
    size_ = size;
  }

  protected int getSize() {
    return size_;
  }

  public int getLength() {
    int l;

    if (getOffset() == 0) {
      l = getBuffer().length;
    } else {
      l = getSize() < getBuffer().length - getOffset() ? getSize() : getBuffer().length - getOffset();
    }
    return l;
  }

  static public CpoCharArrayReader getCpoReader(Reader r) {
    CpoCharArrayReader ccar = null;
    if (r instanceof CpoCharArrayReader) {
      ccar = ((CpoCharArrayReader) r);
    } else {
      // Need to determine the length of the Reader
      // Need to determine the length of the InputStream
      int b;
      try {
        CharArrayWriter caw = new CharArrayWriter();
        while ((b = r.read()) != -1) {
          caw.write(b);
        }
        ccar = new CpoCharArrayReader(caw.toCharArray());
      } catch (IOException ioe) {
        // do nothing for now. The null should get someone's attention. 
      } finally {
        try {
          r.close();
        } catch (IOException ioe) {
        }
      }
    }

    return ccar;
  }
}