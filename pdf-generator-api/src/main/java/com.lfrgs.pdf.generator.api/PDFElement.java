package com.lfrgs.pdf.generator.api;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

/**
 * @author Shane Merriss
 */
public abstract class PDFElement<T extends PDFElement<T>> {

	public PDFElement() {
	}

	public PDFElement(float height, float width, float[] margins) {
		_height = height;
		_width = width;
		_margins = ArrayUtil.clone(margins);
	}

	public abstract void draw(PDFContextWriter pdfContextWriter, float startX, float startY) throws IOException;

	public float getHeight() {
		return _height;
	}

	public float getMarginBottom() {
		return getMargins()[MARGIN.BOTTOM.get()];
	}

	public float getMarginLeft() {
		return getMargins()[MARGIN.LEFT.get()];
	}

	public float getMarginRight() {
		return getMargins()[MARGIN.RIGHT.get()];
	}

	public float[] getMargins() {
		return _margins;
	}

	public float getMarginTop() {
		return getMargins()[MARGIN.TOP.get()];
	}

	public float[] getPageMargins() {
		return _pageMargins;
	}

	public PDRectangle getPageRectangle() {
		return _pageRectangle;
	}

	public float getPageWritableHeight() {
		return getPageRectangle().getHeight()
			- getPageMargins()[MARGIN.TOP.get()]
			- getPageMargins()[MARGIN.BOTTOM.get()];
	}

	public abstract T getPDFElement();

	public float getWidth() {
		return _width;
	}

	public float getWritableHeight() {
		return getHeight() - getMarginTop() - getMarginBottom();
	}

	public float getWritableWidth() {
		return getWidth() - getMarginLeft() - getMarginRight();
	}

	public T setBottomMargin(float bottomMargin) {
		_margins[MARGIN.BOTTOM.get()] = bottomMargin;

		return getPDFElement();
	}

	public T setHeight(float height) {
		_height = height;

		return getPDFElement();
	}

	public T setLeftMargin(float leftMargin) {
		_margins[MARGIN.LEFT.get()] = leftMargin;

		return getPDFElement();
	}

	public T setMargins(float topMargin, float bottomMargin, float leftMargin, float rightMargin) {
		_margins[MARGIN.TOP.get()] = topMargin;
		_margins[MARGIN.BOTTOM.get()] = bottomMargin;
		_margins[MARGIN.LEFT.get()] = leftMargin;
		_margins[MARGIN.RIGHT.get()] = rightMargin;

		return getPDFElement();
	}

	public T setMargins(float[] margins) {
		_margins = margins;

		return getPDFElement();
	}

	public T setPageMargins(float[] pageMargins) {
		_pageMargins = pageMargins;

		return getPDFElement();
	}

	public T setPageRectangle(PDRectangle pdRectangle) {
		_pageRectangle = pdRectangle;

		return getPDFElement();
	}

	public T setRightMargin(float rightMargin) {
		_margins[MARGIN.RIGHT.get()] = rightMargin;

		return getPDFElement();
	}

	public T setTopMargin(float topMargin) {
		_margins[MARGIN.TOP.get()] = topMargin;

		return getPDFElement();
	}

	public T setWidth(float width) {
		_width = width;

		return getPDFElement();
	}

	public enum MARGIN {
		TOP(0),
		BOTTOM(1),
		LEFT(2),
		RIGHT(3);

		MARGIN(int value) {
			_value = value;
		}

		public int get() {
			return _value;
		}

		private int _value;
	}

	private static float[] _pageMargins = ArrayUtil.clone(PDFConstants.DEFAULT_DOCUMENT_MARGINS);
	private static PDRectangle _pageRectangle = PDRectangle.LETTER;
	private float _height = 3f;
	private float[] _margins = ArrayUtil.clone(PDFConstants.DEFAULT_MARGINS); 	// top, bottom, left, right
	private float _width;
}