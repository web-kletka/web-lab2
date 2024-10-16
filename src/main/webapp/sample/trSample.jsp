<%--
  Created by IntelliJ IDEA.
  User: ra
  Date: 16.10.2024
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.model.common.models.RequestEntryModel" %>
<%
  RequestEntryModel entry = (RequestEntryModel) request.getAttribute("entry");
%>
<table>
  <tbody id="myTBody">
<tr>
  <td><%= entry.getXYR()%></td>
  <td><%= entry != null ? request.getAttribute("result") : "None"%></td>
  <td><%= entry != null ? entry.getDate() : "None"%></td>
  <td><%= entry != null ? entry.getTime() : "None"%></td>
</tr>
  </tbody>
</table>