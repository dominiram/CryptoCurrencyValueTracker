package app.naum.myapplication.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import java.lang.Exception

class GraphViewCanvas @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    private var lines: Path = Path()
    private var graphLine: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        graphLine.apply {
            style = Paint.Style.STROKE
            setARGB(255, 0, 255, 0)
            strokeWidth = 5F
            isAntiAlias = true
        }
        lines.moveTo(0F, 0F)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        lines.lineTo(200F, 50F)
        lines.lineTo(300F, 150F)
        lines.lineTo(400F, 250F)
        lines.lineTo(500F, 300F)

        canvas?.drawPath(lines, graphLine)
    }
}