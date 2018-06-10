package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatSeekBar;

public class TextThumbSeekBar extends AppCompatSeekBar {

    private TextPaint mTextPaint;

    public TextThumbSeekBar(Context context) {
        this(context, null);
    }

    public TextThumbSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.seekBarStyle);
    }

    public TextThumbSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(45);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mins = getProgress() / 100;
        int secs = getProgress() % 100;
        StringBuffer sB = new StringBuffer();
        sB
                .append(mins)
                .append(":");
        if (secs < 10) {
            sB.append("0" + secs);
        } else {
            sB.append(secs);
        }
        String progressText = sB.toString();
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(progressText, 0, progressText.length(), bounds);

        int leftPadding = getPaddingLeft() - getThumbOffset();
        int rightPadding = getPaddingRight() - getThumbOffset();
        int width = getWidth() - leftPadding - rightPadding;
        float progressRatio = (float) getProgress() / getMax();
        float thumbX = progressRatio * width + leftPadding;
        int thumbOffset = 30;
        float thumbY = getHeight() / 2f + bounds.height() / 2f - thumbOffset;
        canvas.drawText(progressText, thumbX, thumbY, mTextPaint);
    }
}
