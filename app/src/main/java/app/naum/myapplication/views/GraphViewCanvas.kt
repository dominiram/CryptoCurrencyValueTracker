package app.naum.myapplication.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import app.naum.myapplication.R
import app.naum.myapplication.models.GraphCoordinatesModel
import java.lang.Exception

class GraphViewCanvas @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    private var lines: Path = Path()
    private var graphLine: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var graphCoordinatesModel: GraphCoordinatesModel? = null

    init {
        graphLine.apply {
            style = Paint.Style.STROKE
            setARGB(255, 0, 255, 0)
            strokeWidth = 7F
            isAntiAlias = true
        }
    }

    fun setGraphCoordinatesModel(graphCoordinatesModel: GraphCoordinatesModel) {
        this.graphCoordinatesModel = graphCoordinatesModel
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        lines.reset()

        canvas?.drawColor(Color.WHITE)

        if(graphCoordinatesModel != null) {
            val model = graphCoordinatesModel!!
            val size = model.xCoordinates.size
            val maxShownValueOnCanvas = model.yMax + (model.yMax + model.yMin) / 2
            lines.moveTo(
                getWidthOnCanvas(0, size),
                transformValueY(
                    model.yCoordinates[0],
                    maxShownValueOnCanvas
                )
            )
            for (i in 1 until model.xCoordinates.size) {
                lines.lineTo(
                    getWidthOnCanvas(i, size),
                    transformValueY(model.yCoordinates[i], maxShownValueOnCanvas)
                )
            }
        }

        graphLine.color = resources.getColor(R.color.cool_gray)
        canvas?.drawPath(lines, graphLine)
        Log.d(CoinGraphViewFragment.TAG, "setupUI: canvas.width = $width")
        Log.d(CoinGraphViewFragment.TAG, "setupUI: canvas.height = $height")
    }

    private fun getWidthOnCanvas(i: Int, size: Int): Float = (width.toFloat() / size.toFloat()) * i

    //    private fun transformTimestampToX(ts: Int, maxTs: Int): Float = (width*(ts.toFloat()/maxTs.toFloat()))
    private fun transformValueY(value: Double, maxVal: Double): Float =
        (height - height * (value / maxVal).toFloat())

    companion object {
        val TAG = "GraphViewCanvas"
    }
}