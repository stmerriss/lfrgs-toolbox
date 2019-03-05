package com.lfrgs.pdf.generator.api;

import java.awt.Color;

import java.io.IOException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @author Shane Merriss
 */
public class PDFDiv extends PDFElement<PDFDiv> {

	public PDFDiv() {
	}

	public PDFDiv(float height, float width, float[] margins, boolean isVerticalOrientation) {
		super(height, width, margins);

		_verticalOrientation = isVerticalOrientation;
	}

	public PDFDiv addPDFElement(PDFElement pdfElement) {
		_pdfElementList.addLast(pdfElement);

		return this;
	}

	public void addPDFElementFirst(PDFElement pdfElement) {
		_pdfElementList.addFirst(pdfElement);
	}

	public void addPDFElements(Collection<? extends PDFElement> pdfElements) {
		pdfElements.forEach(this::addPDFElement);
	}

	@Override
	public void draw(PDFContextWriter pdfContextWriter, float startX, float startY) throws IOException {
		if (_isHeaderDiv) {
			pdfContextWriter.setHeaderPDFDiv(this);
		}

		float drawX = startX + super.getMarginLeft();
		float drawY = startY - super.getMarginTop();

		float divHeight = getHeight();

		PDFDiv headerDiv = pdfContextWriter.getHeaderPDFDiv();

		float pageWritableHeight = getPageWritableHeight();
		float headerDivHeight = 0f;

		if (!Objects.isNull(headerDiv)) {
			headerDivHeight = headerDiv.getHeight();
			pageWritableHeight -= headerDivHeight;
		}

		if ((drawY < divHeight) && (divHeight < pageWritableHeight)) {
			pdfContextWriter.addNewPage();

			startY = pdfContextWriter.getRectangleHeight() - PDFConstants.DEFAULT_DOCUMENT_MARGINS[MARGIN.TOP.get()];

			drawY = startY - super.getMarginTop();

			if (!Objects.isNull(headerDiv)) {
				headerDiv.draw(pdfContextWriter, drawX, drawY);

				drawY -= headerDivHeight;
			}

			drawPDFDiv(pdfContextWriter, drawX, drawY);
		}
		else {
			drawPDFDiv(pdfContextWriter, startX, startY);
		}
	}

	public void drawBorder(PDFContextWriter pdfContextWriter, float startX, float startY, float fillHeight)
		throws IOException {

		pdfContextWriter.write(
			(pdPageContentStream) -> {
				pdPageContentStream.saveGraphicsState();
				pdPageContentStream.setNonStrokingColor(getBorderColor());
				pdPageContentStream.addRect(startX, startY, getWidth(), fillHeight);
				pdPageContentStream.fill();
				pdPageContentStream.restoreGraphicsState();
			});
	}

	public void drawPDFDiv(PDFContextWriter pdfContextWriter, float startX, float startY) throws IOException {
		float drawX = startX + super.getMarginLeft();
		float drawY = startY - super.getMarginTop();

		float divHeight = getHeight();

		float fillHeight = Math.min(getTopBorderLineHeight(), getMarginTop());

		if (fillHeight > 0) {
			drawBorder(pdfContextWriter, startX, drawY, fillHeight);
		}

		int pageNumber = pdfContextWriter.getNumberOfPages();

		for (PDFElement pdfElement : _pdfElementList) {
			pdfElement.draw(pdfContextWriter, drawX, drawY);

			if (_verticalOrientation) {
				if (pageNumber < pdfContextWriter.getNumberOfPages()) {

					pageNumber = pdfContextWriter.getNumberOfPages();

					drawY = pdfContextWriter.getRectangleHeight()
						- pdfContextWriter.getHeaderPDFDiv().getHeight()
						- PDFConstants.DEFAULT_DOCUMENT_MARGINS[MARGIN.TOP.get()];
				}

				drawY -= pdfElement.getHeight();
			}
			else {
				drawX += pdfElement.getWidth();
			}
		}

		fillHeight = Math.min(getBottomBorderLineHeight(), getMarginBottom());

		if (fillHeight > 0) {
			drawBorder(pdfContextWriter, startX, startY - divHeight, fillHeight);
		}
	}

	public Color getBorderColor() {
		return _borderColor;
	}

	public float getBottomBorderLineHeight() {
		if (Objects.isNull(_bottomBorderLineHeight)) {
			_bottomBorderLineHeight = 0f;
		}

		return _bottomBorderLineHeight;
	}

	@Override
	public float getHeight() {
		float marginHeight = super.getMarginTop() + super.getMarginBottom();

		if (!_verticalOrientation) {
			return marginHeight +
				_pdfElementList.stream()
					.map(PDFElement::getHeight)
					.reduce(0f, Math::max);
		}

		if (_pdfElementList.isEmpty()) {
			return marginHeight + super.getHeight();
		}

		return marginHeight +
			_pdfElementList.stream()
				.map(PDFElement::getHeight)
				.reduce(0f, Float::sum);
	}

	@Override
	public PDFDiv getPDFElement() {
		return this;
	}

	public float getTopBorderLineHeight() {
		return _topBorderLineHeight;
	}

	public PDFDiv setBorderColor(Color color) {
		_borderColor = color;

		return this;
	}

	public PDFDiv setBottomBorderLine(float bottomBorderLineHeight) {
		_bottomBorderLineHeight = bottomBorderLineHeight;

		return this;
	}

	public PDFDiv setIsHeaderDiv(boolean isHeaderDiv) {
		_isHeaderDiv = isHeaderDiv;

		return this;
	}

	public PDFDiv setTopBorderLine(float topBorderLineHeight) {
		_topBorderLineHeight = topBorderLineHeight;

		return this;
	}

	public PDFDiv setVerticalOrientation(boolean verticalOrientation) {
		_verticalOrientation = verticalOrientation;

		return this;
	}

	private Color _borderColor = Color.LIGHT_GRAY;
	private Float _bottomBorderLineHeight;
	private boolean _isHeaderDiv;
	private LinkedList<PDFElement> _pdfElementList = new LinkedList<>();
	private float _topBorderLineHeight;

	// top, bottom, left, right

	private boolean _verticalOrientation = true;

}