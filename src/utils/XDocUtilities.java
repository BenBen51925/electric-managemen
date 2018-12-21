/*
 * XDoc
 *
 * Copyright (C) 2004, Jörg Kiegeland <xdoc@kiegeland.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package utils;


// Imported java.io classes
// Imported SAX classes
// Imported Serializer classes
// Imported JAVA API for XML Parsing classes
//import xni.Counter;
import java.util.*;

import com.sun.org.apache.xerces.internal.xs.XSObjectList;
import com.sun.org.apache.xerces.internal.xs.XSSimpleTypeDefinition;

import org.w3c.dom.*;


public class XDocUtilities {

  public static Vector getAttrChildren(Node node) {
    Vector res=new Vector();
    for (int i=0; i<node.getAttributes().getLength(); i++)
      res.add(node.getAttributes().item(i));
    return res;
  }


  public static boolean AllowsValueOfType(XSSimpleTypeDefinition st, String type) {
    if (type.equals(st.getName()))
      return true;
    XSObjectList members=st.getMemberTypes();
    if (members!=null)
    for (int i=0; i<members.getLength(); i++)
      if (AllowsValueOfType( (XSSimpleTypeDefinition) members.item(i),type))
        return true;
    return false;
  }

  static Vector UniqueVector(Vector v) {
    Vector res_unique=new Vector();
    for (int i=0; i<v.size(); i++)
      if (res_unique.indexOf(v.get(i))==-1)
        res_unique.add(v.get(i));
    return res_unique;
  }

  static int FindStringInVector(String s, Vector v) {
    for (int i=0; i<v.size(); i++)
      if (v.get(i).equals(s))
        return i;
    return -1;
  }

  static int FindStringInStrings(String s, String[] v) {
    for (int i=0; i<v.length; i++)
      if (v[i].equals(s))
        return i;
    return -1;
  }

  static Vector UniqueStringVector(Vector v) {
    for (int i=v.size()-1; i>=0; i--)
      for (int i2=0; i2<i; i2++)
        if (v.get(i2).equals(v.get(i))) {
          v.remove(i);
          break;
        }
    return v;
  }


  static Vector SubtractVector(Vector v,Vector subtract) {
    Vector res_subtract=new Vector();
    for (int i=0; i<v.size(); i++)
      if (subtract.indexOf(v.get(i))==-1)
        res_subtract.add(v.get(i));
    return res_subtract;
  }

  void DeleteVectorRange(Vector v, int range_start, int range_end) {
    int block_to_move=v.size()-range_end;
    for (int i=0; i<block_to_move; i++)
      v.set(range_start+i,v.get(range_end+i));
    v.setSize(range_start+block_to_move);
  }

  static Node global_findNode;

  static protected int searchInDom(Node node, int findIndex, Node findNode, int currentIndex) {
    if (! (node instanceof Element))
      return currentIndex;
    currentIndex--;
    if (findIndex == -currentIndex || findNode == node) {
      global_findNode=node;
      return -currentIndex;
    }
    node = node.getFirstChild();
    while (node != null) {
      currentIndex = searchInDom(node, findIndex,findNode, currentIndex);
      if (currentIndex > 0)
        return currentIndex;
      node = node.getNextSibling();
    }
    return currentIndex;
  }

  //return n, if "find" is the n-th element in the source document
  //the document element has number 1
  //returns a number <=0 if the "find" cannot be found
  static public int getElementIndex(Node find) {
    return find!=null ? searchInDom(find.getOwnerDocument().getDocumentElement(),-1,find,0) : 0;
  }

  //returns the n-th element
  //returns null if the document has less then n elements
  static public Node getElementWithIndex(Document doc, int n) {
    global_findNode=null;
    if (doc!=null && doc.getDocumentElement()!=null && n>=1)
      searchInDom(doc.getDocumentElement(),n,null,0);
    return global_findNode;
  }





}