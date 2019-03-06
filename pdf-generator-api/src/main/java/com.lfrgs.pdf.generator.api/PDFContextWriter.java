package com.lfrgs.pdf.generator.api;

import com.liferay.gs.util.dto.Pair;
import com.liferay.gs.util.operations.UnsafeConsumer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.util.Locale;
import java.util.Objects;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * @author Shane Merriss
 */
public class PDFContextWriter implements AutoCloseable {

	public PDFContextWriter(PDRectangle pdRectangle) {
		_pdRectangle = pdRectangle;
		_pdPage = new PDPage(_pdRectangle);
		_pdDocument = new PDDocument();
		_pdStream = new PDStream(_pdDocument);
		_pdDocument.addPage(_pdPage);
	}

	public void addNewPage() throws IOException {
		_pdPageContentStream.close();
		_pdPage = new PDPage(_pdRectangle);
		_pdDocument.addPage(_pdPage);
		_pdPageContentStream = new PDPageContentStream(_pdDocument, _pdPage);
	}

	@Override
	public void close() throws IOException {
		closePageContentStream();

		_pdDocument.close();
	}

	public void closePageContentStream() throws IOException {
		_pdPageContentStream.close();

		_pdDocument.save(_pdStream.createOutputStream());
	}

	public PDFDiv getHeaderPDFDiv() {
		return _headerPDFDiv;
	}

	public int getNumberOfPages() {
		return _pdDocument.getNumberOfPages();
	}

	public float getRectangleHeight() {
		return _pdRectangle.getHeight();
	}

	public void setHeaderPDFDiv(PDFDiv headerPDFDiv) {
		_headerPDFDiv = headerPDFDiv;
	}

	public byte[] toByteArray() throws IOException {
		return _pdStream.toByteArray();
	}

	public void write(UnsafeConsumer<PDPageContentStream, IOException> writer) throws IOException {
		if (Objects.isNull(_pdPageContentStream)) {
			_pdPageContentStream = new PDPageContentStream(_pdDocument, _pdPage);
		}

		writer.accept(_pdPageContentStream);
	}

	public Pair<PDFont> getCountryBasedFonts(Locale locale) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Pair<PDFont> fontPair = new Pair<>();

//		if (locale.equals(Locale.JAPAN)) {
//			try {
//				fontPair.setFirst(PDType0Font.load(
//					_pdDocument,
//					classLoader.getResourceAsStream(_JAPAN_FONTS)));
//				fontPair.setSecond(PDType0Font.load(
//					_pdDocument,
//					classLoader.getResourceAsStream(_JAPAN_FONTS_BOLD)));
//			}
//			catch (IOException ioe) {
//				_log.error(ioe, ioe);
//			}
//		}
//		else
		if (locale.equals(Locale.FRANCE)) {
			try {
				fontPair.setFirst(PDType0Font.load(_pdDocument, classLoader.getResourceAsStream(_ARIAL_FONTS)));
				fontPair.setSecond(PDType0Font.load(_pdDocument, classLoader.getResourceAsStream(_ARIAL_FONTS_BOLD)));
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}
		else {
			fontPair.setFirst(PDType1Font.HELVETICA);
			fontPair.setSecond(PDType1Font.HELVETICA_BOLD);
		}

		return fontPair;
	}

	private static final String _ARIAL_FONTS = "com/lfrgs/pdf/fonts/arial.ttf";

	private static final String _ARIAL_FONTS_BOLD = "com/lfrgs/pdf/fonts/arialbd.ttf";

	private static Log _log = LogFactoryUtil.getLog(PDFContextWriter.class);

	private PDFDiv _headerPDFDiv = new PDFDiv().setWidth(0f);
	private PDDocument _pdDocument;
	private PDPage _pdPage;
	private PDPageContentStream _pdPageContentStream;
	private PDRectangle _pdRectangle;
	private PDStream _pdStream;

}