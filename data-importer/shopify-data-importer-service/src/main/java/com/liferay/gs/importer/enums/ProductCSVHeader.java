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

package com.liferay.gs.importer.enums;

/**
 * @author Jared Domio
 */
public enum ProductCSVHeader {

	BODY("Body (HTML)"),
	COST_PER_ITEM("Cost per item"),
	GIFT_CARD("Gift Card"),
	HANDLE("Handle"),
	IMAGE_ALT_TEXT("Image Alt Text"),
	IMAGE_POSITION("Image Position"),
	IMAGE_SRC("Image Src"),
	OPTION1_NAME("Option1 Name"),
	OPTION1_VALUE("Option1 Value"),
	OPTION2_NAME("Option2 Name"),
	OPTION2_VALUE("Option2 Value"),
	OPTION3_NAME("Option3 Name"),
	OPTION3_VALUE("Option3 Value"),
	PUBLISHED("Published"),
	SEO_DESCRIPTION("SEO Description"),
	SEO_TITLE("SEO Title"),
	TAGS("Tags"),
	TITLE("Title"),
	TYPE("Type"),
	VARIANT_BARCODE("Variant Barcode"),
	VARIANT_COMPARE_AT_PRICE("Variant Compare At Price"),
	VARIANT_FULFILLMENT_SERVICE("Variant Fulfillment Service"),
	VARIANT_GRAMS("Variant Grams"),
	VARIANT_IMAGE("Variant Image"),
	VARIANT_INVENTORY_POLICY("Variant Inventory Policy"),
	VARIANT_INVENTORY_QTY("Variant Inventory Qty"),
	VARIANT_INVENTORY_TRACKER("Variant Inventory Tracker"),
	VARIANT_PRICE("Variant Price"),
	VARIANT_REQUIRES_SHIPPING("Variant Requires Shipping"),
	VARIANT_SKU("Variant SKU"),
	VARIANT_TAX_CODE("Variant Tax Code"),
	VARIANT_TAXABLE("Variant Taxable"),
	VARIANT_WEIGHT_UNIT("Variant Weight Unit"),
	VENDOR("Vendor");

	public String toString() {
		return _columnName;
	}

	private ProductCSVHeader(String columnName) {
		_columnName = columnName;
	}

	private String _columnName;

}
