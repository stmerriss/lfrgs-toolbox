package com.lfrgs.pdf.generator.api;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * @author Shane Merriss
 */
public class PDFConstants {

	// top, bottom, left, right

	public static final float[] DEFAULT_DOCUMENT_MARGINS = {31f, 31f, 20f, 20f};

	public static final PDFont DEFAULT_FONT = PDType1Font.HELVETICA;

	public static final PDFont DEFAULT_FONT_BOLD = PDType1Font.HELVETICA_BOLD;

	public static final float DEFAULT_FONT_SIZE = 9f;

	public static final float DEFAULT_LINE_SPACING_SCALE = 1.25f;

	// top, bottom, left, right

	public static final float[] DEFAULT_MARGINS = {2f, 2f, 0f, 0f};

}