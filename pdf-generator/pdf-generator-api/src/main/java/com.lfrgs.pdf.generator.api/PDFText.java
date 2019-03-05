package com.lfrgs.pdf.generator.api;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.font.PDFont;

/**
 * @author Shane Merriss
 */
public class PDFText extends PDFElement<PDFText> {

	@Override
	public void draw(PDFContextWriter pdfContextWriter, float startX, float startY) throws IOException {
		float drawX = startX + super.getMarginLeft();
		float drawY = startY - super.getMarginTop() - getFontHeight();

		pdfContextWriter.write(
			(pdPageContentStream) -> {
				pdPageContentStream.beginText();

				float leading = getLineSpacing() + getFontHeight();

				pdPageContentStream.setLeading(leading);
				pdPageContentStream.newLineAtOffset(drawX, drawY);
				pdPageContentStream.setFont(_pdFont, _fontSize);

				for (String str : getTextList()) {
					float tx = 0f;

					try {
						if (isRightAlignment()) {
							tx = super.getWritableWidth()
								- super.getMarginRight()
								- _convertTextToPageUnits(_pdFont.getStringWidth(str));

							pdPageContentStream.newLineAtOffset(tx, 0f);
						}

						pdPageContentStream.showText(str);
						pdPageContentStream.newLineAtOffset(-tx, -leading);
					}
					catch (Exception e) {
						if (_log.isDebugEnabled()) {
							StringBundler sb = new StringBundler(7);

							sb.append("{font:");
							sb.append(_pdFont.getName());

							if (!Objects.isNull(_pdFont.getFontDescriptor())) {
								sb.append(", fontCharSet:");
								sb.append(_pdFont.getFontDescriptor().getCharSet());
							}

							sb.append(", fontSize:");
							sb.append(_fontSize);
							sb.append("}");

							_log.debug(sb.toString());
						}

						if (_log.isWarnEnabled()) {
							_log.warn("unable to write text " + str, e);
						}
					}
				}

				pdPageContentStream.endText();
			});
	}

	public float getAverageFontWidth() {
		return _convertTextToPageUnits(_pdFont.getFontDescriptor().getAverageWidth());
	}

	public float getFontHeight() {
		return _convertTextToPageUnits(_pdFont.getFontDescriptor().getCapHeight());
	}

	public float getHeight() {
		int lines = getTextList().size();

		float totalTextHeight = lines * getFontHeight();
		float totalLineSpacingHeight = getLineSpacing() * (lines - 1);

		return super.getMarginTop()
			+ super.getMarginBottom()
			+ totalLineSpacingHeight
			+ totalTextHeight;
	}

	public float getLineSpacing() {
		return getFontHeight() * getLineSpacingScale();
	}

	public float getLineSpacingScale() {
		return _lineSpacingScale;
	}

	@Override
	public PDFText getPDFElement() {
		return this;
	}

	public String getText() {
		return _text;
	}

	public List<String> getTextList() {
		return _parseTextToList(_text);
	}

	public boolean isRightAlignment() {
		return _isRightAlignment;
	}

	public PDFText setAlignmentRight() {
		_isRightAlignment = true;

		return this;
	}

	public PDFText setFont(PDFont pdFont) {
		_pdFont = pdFont;

		return this;
	}

	public PDFText setFontSize(float fontSize) {
		_fontSize = fontSize;

		return this;
	}

	public PDFText setText(String text) {
		_text = text;

		return this;
	}

	private float _convertTextToPageUnits(float textWidth) {
		return textWidth / 1000 * _fontSize;
	}

	private List<String> _parseTextToLength(String text) {
		List<String> textList = new ArrayList<>();

		float writableWidth = super.getWritableWidth();
		float averageFontWidth = getAverageFontWidth();

		int averageCharactersPerLine = (int)Math.floor(writableWidth / averageFontWidth);

		try {
			float textWidth = _convertTextToPageUnits(_pdFont.getStringWidth(text.trim()));

			if (textWidth <= writableWidth) {
				textList.add(text.trim());

				return textList;
			}

			int lowIndex = 0;
			int highIndex = text.length();

			while (lowIndex < highIndex) {
				String subText = text.substring(lowIndex, highIndex);

				textWidth = _convertTextToPageUnits(_pdFont.getStringWidth(subText));

				if ((textWidth > writableWidth) && (subText.lastIndexOf(StringPool.SPACE) == -1)) {

					textList.add(subText.substring(0, averageCharactersPerLine));

					lowIndex += averageCharactersPerLine;

					highIndex = lowIndex + averageCharactersPerLine;

					if ((highIndex > text.length()) || (highIndex < 0)) {
						highIndex = text.length();

						continue;
					}
				}

				if (textWidth <= writableWidth) {
					textList.add(subText);

					lowIndex = highIndex;

					highIndex += averageCharactersPerLine;

					if ((highIndex > text.length()) || (highIndex < 0)) {
						highIndex = text.length();

						continue;
					}
				}

				highIndex = text.substring(0, highIndex).lastIndexOf(StringPool.SPACE);
			}
		}
		catch (IOException ioe) {
			_log.error("Cannot get string width from font. Trimming text to length.", ioe);

			textList.add(text.substring(0, Math.min(text.length() - 1, averageCharactersPerLine - 1)));
		}

		return textList;
	}

	private List<String> _parseTextToList(String text) {
		String[] newLineSplitArray = StringUtil.split(text, "\n");

		List<String> textList = ListUtil.toList(newLineSplitArray);

		textList = textList.stream()
			.flatMap(str -> _parseTextToLength(str).stream())
			.collect(Collectors.toList());

		return textList;
	}

	private static Log _log = LogFactoryUtil.getLog(PDFText.class);

	private Float _fontSize = PDFConstants.DEFAULT_FONT_SIZE;
	private boolean _isRightAlignment;
	private Float _lineSpacingScale = PDFConstants.DEFAULT_LINE_SPACING_SCALE;
	private PDFont _pdFont = PDFConstants.DEFAULT_FONT;
	private String _text = StringPool.BLANK;

}