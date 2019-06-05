<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-portlet:actionURL name="/import/products" var="importProductActionURL">
</liferay-portlet:actionURL>

<aui:form action="${importProductActionURL}" name="fm">
	<aui:input cssClass="btn" name="csv-file" type="file">
		<aui:validator name="acceptFiles">'csv'</aui:validator>
	</aui:input>

	<aui:button id="btnSubmit" name="import" type="submit" value="import" />
</aui:form>