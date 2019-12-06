package kr.yeon.android.shadow

import android.graphics.*
import android.graphics.drawable.Drawable

class ShadowDrawable(private var blurSize: Float, private var x: Float, private var y: Float,
                     private var startColor: Int, private var endColor: Int,

                     private var bg: Int, private var radius: Float,
                     private var border: Int, private var borderSize: Float): Drawable() {

    companion object {
        internal const val DEFAULT_START_COLOR = Color.BLACK
        internal const val DEFAULT_END_COLOR = Color.TRANSPARENT
        internal const val DEFAULT_BLUR_SIZE = 0f
        internal const val DEFAULT_X = 0f
        internal const val DEFAULT_Y = 0f
        internal const val DEFAULT_RADIUS = 0f
        internal const val DEFAULT_BG_COLOR = Color.WHITE
        internal const val DEFAULT_BORDER_COLOR = Color.BLACK
        internal const val DEFAULT_BORDER_SIZE = 0f
    }

    // shadow padding
    private var padding: Rect = Rect(0,0, 0, 0)

    // paint
    private var gradientPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bgPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)


    // gradient
    private var gradientTop: LinearGradient? = null
    private var gradientBottom: LinearGradient? = null
    private var gradientLeft: LinearGradient? = null
    private var gradientRight: LinearGradient? = null

    private var gradientLeftTop: RadialGradient? = null
    private var gradientLeftBottom: RadialGradient? = null
    private var gradientRightTop: RadialGradient? = null
    private var gradientRightBottom: RadialGradient? = null


    // rect
    private var rectGradientTop: RectF = RectF()
    private var rectGradientBottom: RectF = RectF()
    private var rectGradientLeft: RectF = RectF()
    private var rectGradientRight: RectF = RectF()

    private var rectGradientLeftTop: RectF = RectF()
    private var rectGradientRightTop: RectF = RectF()
    private var rectGradientLeftBottom: RectF = RectF()
    private var rectGradientRightBottom: RectF = RectF()

    private var rectGradientBg: RectF = RectF()
    private var rectBg: RectF = RectF()
    

    constructor(): this(DEFAULT_BLUR_SIZE, DEFAULT_X, DEFAULT_Y, DEFAULT_START_COLOR, DEFAULT_END_COLOR, DEFAULT_BG_COLOR, DEFAULT_RADIUS, DEFAULT_BORDER_COLOR, DEFAULT_BORDER_SIZE)
    constructor(blurSize: Float, x: Float, y: Float): this(blurSize, x, y, DEFAULT_START_COLOR, DEFAULT_END_COLOR, DEFAULT_BG_COLOR, DEFAULT_RADIUS, DEFAULT_BORDER_COLOR, DEFAULT_BORDER_SIZE)


    init {
        padding.set((blurSize - x).toInt(), (blurSize - y).toInt(), (blurSize + x).toInt(), (blurSize + y).toInt())
    }


    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)

        if(bounds == null) {
            return
        }

        val shadowLeft = blurSize + radius
        val shadowRight = bounds.right - shadowLeft
        val shadowTop = shadowLeft
        val shadowBottom = bounds.bottom - shadowTop
        val innerPoint = blurSize / shadowLeft
        val innerPadding = innerPoint * 0.1f // actually this value maybe 0, but too dark.

        // size
        rectBg.set(blurSize - x, blurSize - y , bounds.right - blurSize - x, bounds.bottom - blurSize - y)

        if(blurSize != 0f) {
            rectGradientBg.set(shadowLeft, shadowTop, shadowRight, shadowBottom)

            rectGradientTop.set(shadowLeft, 0f, shadowRight, shadowTop)
            rectGradientBottom.set(shadowLeft, shadowBottom, shadowRight, bounds.bottom.toFloat())

            rectGradientLeft.set(0f, shadowTop, shadowLeft, shadowBottom)
            rectGradientRight.set(shadowRight, shadowTop, bounds.right.toFloat(), shadowBottom)

            rectGradientLeftTop.set(0f, 0f, shadowLeft, shadowTop)
            rectGradientRightTop.set(shadowRight, 0f, bounds.right.toFloat(), shadowTop)

            rectGradientLeftBottom.set(0f, shadowBottom, shadowLeft, bounds.bottom.toFloat())
            rectGradientRightBottom.set(
                shadowRight,
                shadowBottom,
                bounds.right.toFloat(),
                bounds.bottom.toFloat()
            )


            val startToEndColor = intArrayOf(startColor, startColor, endColor, endColor)
            val startToEndPosition = floatArrayOf(0f, 1 - innerPoint - innerPadding, 1 - innerPadding, 1f)
            val endToStartColor = intArrayOf(endColor, endColor, startColor, startColor)
            val endToStartPosition = floatArrayOf(0f, innerPadding, innerPoint + innerPadding, 1f)
            // color
            gradientTop = LinearGradient(
                rectGradientTop.left,
                rectGradientTop.top,
                rectGradientTop.left,
                rectGradientTop.bottom,
                endToStartColor,
                endToStartPosition,
                Shader.TileMode.CLAMP
            )
            gradientBottom = LinearGradient(
                rectGradientBottom.left,
                rectGradientBottom.top,
                rectGradientBottom.left,
                rectGradientBottom.bottom,
                startToEndColor,
                startToEndPosition,
                Shader.TileMode.CLAMP
            )

            gradientLeft = LinearGradient(
                rectGradientLeft.left,
                rectGradientLeft.top,
                rectGradientLeft.right,
                rectGradientLeft.top,
                endToStartColor,
                endToStartPosition,
                Shader.TileMode.CLAMP
            )
            gradientRight = LinearGradient(
                rectGradientRight.left,
                rectGradientRight.top,
                rectGradientRight.right,
                rectGradientRight.top,
                startToEndColor,
                startToEndPosition,
                Shader.TileMode.CLAMP
            )

            gradientLeftTop = RadialGradient(
                shadowLeft,
                shadowTop,
                shadowLeft,
                startToEndColor,
                startToEndPosition,
                Shader.TileMode.CLAMP
            )
            gradientRightTop = RadialGradient(
                shadowRight,
                shadowTop,
                shadowLeft,
                startToEndColor,
                startToEndPosition,
                Shader.TileMode.CLAMP
            )

            gradientLeftBottom = RadialGradient(
                shadowLeft,
                shadowBottom,
                shadowLeft,
                startToEndColor,
                startToEndPosition,
                Shader.TileMode.CLAMP
            )
            gradientRightBottom = RadialGradient(
                shadowRight,
                shadowBottom,
                shadowLeft,
                startToEndColor,
                startToEndPosition,
                Shader.TileMode.CLAMP
            )
        }
    }


    override fun draw(canvas: Canvas) {
        // top, bottom, left, right shadow
        if(blurSize != 0f) {
            canvas.drawRect(rectGradientTop, gradientPaint.apply { shader = gradientTop })
            canvas.drawRect(rectGradientBottom, gradientPaint.apply { shader = gradientBottom })
            canvas.drawRect(rectGradientLeft, gradientPaint.apply { shader = gradientLeft })
            canvas.drawRect(rectGradientRight, gradientPaint.apply { shader = gradientRight })

            // corner shadow
            canvas.drawRoundRect(rectGradientLeftTop,
                rectGradientLeftTop.right - rectGradientLeftTop.left,
                0f, gradientPaint.apply { shader = gradientLeftTop })
            canvas.drawRoundRect(
                rectGradientRightTop,
                rectGradientRightTop.right - rectGradientRightTop.left, 0f,
                gradientPaint.apply { shader = gradientRightTop })
            canvas.drawRoundRect(rectGradientLeftBottom,
                rectGradientLeftBottom.right - rectGradientLeftBottom.left,
                0f, gradientPaint.apply { shader = gradientLeftBottom })
            canvas.drawRoundRect(rectGradientRightBottom,
                rectGradientRightBottom.right - rectGradientRightBottom.left,
                0f, gradientPaint.apply { shader = gradientRightBottom })

            // shadow background
            canvas.drawRect(rectGradientBg, bgPaint.apply {
                color = startColor
                style = Paint.Style.FILL
            })
        }

        // draw background
        canvas.drawRoundRect(rectBg , radius, radius, bgPaint.apply {
            color = bg
            style = Paint.Style.FILL
        })
        canvas.drawRoundRect(rectBg , radius, radius, bgPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = borderSize
            color = if(strokeWidth == 0f) bg else border
        })
    }

    override fun setAlpha(alpha: Int) {
        bgPaint.alpha = alpha
        gradientPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        bgPaint.colorFilter = colorFilter
        gradientPaint.colorFilter = colorFilter
    }


    override fun getPadding(padding: Rect): Boolean {
        padding.set(this.padding)
        return (padding.left or padding.top or padding.right or padding.bottom) != 0
    }


    class Builder {
        var blurSize: Float = DEFAULT_BLUR_SIZE
        var x: Float = DEFAULT_X
        var y: Float = DEFAULT_Y

        var startColor: Int = DEFAULT_START_COLOR
        var endColor: Int = DEFAULT_END_COLOR

        var bg: Int = DEFAULT_BG_COLOR
        var radius: Float = DEFAULT_RADIUS

        var border: Int = DEFAULT_BORDER_COLOR
        var borderSize: Float = DEFAULT_BORDER_SIZE

        fun build() = ShadowDrawable(blurSize, x, y, startColor, endColor, bg, radius, border, borderSize)
    }
}