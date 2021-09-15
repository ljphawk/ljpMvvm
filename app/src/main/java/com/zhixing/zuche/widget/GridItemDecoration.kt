package com.zhixing.zuche.widget

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


/*
 *@创建者       L_jp
 *@创建时间     2020/11/5 3:07 PM.
 *@描述
 */
class GridItemDecoration(
    context: Context,
    dividerDrawable: Drawable? = null,
    private var hasBorder: Boolean = false
) :
    RecyclerView.ItemDecoration() {

    private val ATTRS = intArrayOf(R.attr.listDivider)

    private var mVerticalDivider: Drawable? = null
    private var mHorizontalDivider: Drawable? = null

    private val mBounds = Rect()

    init {
        if (dividerDrawable == null) {
            val a = context.obtainStyledAttributes(ATTRS);
            mVerticalDivider = a.getDrawable(0)
            mHorizontalDivider = a.getDrawable(0)
            a.recycle();
        } else {
            mVerticalDivider = dividerDrawable
            mHorizontalDivider = dividerDrawable
        }
    }


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        super.getItemOffsets(outRect, view, parent, state);
        if (mVerticalDivider == null && mHorizontalDivider == null) {
            outRect[0, 0, 0] = 0
            return
        }
        //列表中item个数
        val itemCount = parent.adapter!!.itemCount
        //列表中位置
        val position = parent.getChildAdapterPosition(view)
        //列数
        val spanCount: Int = getSpanCount(parent)

        //左右偏移
        //行中位置
        val indexHorizontal = position % spanCount
        //纵向分割线宽度
        val dividerWidth = mVerticalDivider?.intrinsicWidth ?: 0
        //有边界  itemWidth = ( 列数 + 1 ) * 分割线宽度 / 列数
        //无边界  itemWidth = ( 列数 - 1 ) * 分割线宽度 / 列数
        //每个item内分割线占用的宽度,
        // 无边框：每个item内分割线占的宽度 = ( item个数 - 1 ) * 分割线宽度 / item个数
        // 有边框：每个item内分割线占的宽度 = ( item个数 + 1 ) * 分割线宽度 / item个数
        val itemDividerWidth =
            (spanCount + if (hasBorder) 1 else -1) * dividerWidth / spanCount
        val left: Int
        val right: Int
        if (hasBorder) {
            //有边框
            // left = ( 行中位置 + 1 ) * 分割线宽度 - 行中位置 * 每个item内分割线占用的宽度
            left = (indexHorizontal + 1) * dividerWidth - indexHorizontal * itemDividerWidth
            //right = 每个item内分割线占用的宽度 - left
            right = itemDividerWidth - left
        } else {
            //无边框
            //left = 行中位置 * ( 分割线宽度 - 每个item内分割线占用的宽度 )
            left = indexHorizontal * (dividerWidth - itemDividerWidth)
            //right = 每个item内分割线占用的宽度 - left
            right = itemDividerWidth - left
        }

        //上下偏移
        //横向分割线高度
        val dividerHeight = mHorizontalDivider?.intrinsicHeight ?: 0
        val top: Int
        val bottom: Int
        if (hasBorder) {
            //有边框，最上面偏移分割线高度，最下面偏移分割线高度，其他都上下各偏移分割线一半的高度
            top = if (isFirstRow(position, spanCount)) dividerHeight else dividerHeight / 2
            bottom = if (isLastRow(
                    position,
                    itemCount,
                    spanCount
                )
            ) dividerHeight else dividerHeight / 2
        } else {
            //无边框，最上面高度偏移0，最下面高度偏移0，其他上下各偏移分割线一半高度
            top = if (isFirstRow(position, spanCount)) 0 else dividerHeight / 2
            bottom = if (isLastRow(position, itemCount, spanCount)) 0 else dividerHeight / 2
        }
        outRect[left, top, right] = bottom
    }

    /**
     * 获取列数
     */
    private fun getSpanCount(parent: RecyclerView): Int {
        var spanCount = -1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager.spanCount
        }
        return spanCount
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        super.onDraw(c, parent, state);
        drawVertical(c, parent)
        drawHorizontal(c, parent)
    }

    /**
     * 绘制水平分割线
     */
    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        //总item数
        val itemCount = parent.adapter!!.itemCount
        //列数
        val spanCount = getSpanCount(parent)
        //每个item宽度
        val itemWidth =
            (parent.width - parent.paddingLeft - parent.paddingRight) / spanCount
        //分割线高度
        val dividerHeight = mVerticalDivider?.intrinsicHeight ?: 0
        var left: Int
        var right: Int
        var bottom: Int
        var top: Int
        var position: Int
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childView: View = parent.getChildAt(i)
            position = parent.getChildAdapterPosition(childView)
            parent.getDecoratedBoundsWithMargins(childView, mBounds)
            //分割线左右坐标
            left = mBounds.left
            right = left + itemWidth
            //每个item下边分割线上边沿（getItemOffsets时每个item多空了一个分割线高度的一半）
            top = mBounds.bottom
            if (isLastRow(position, itemCount, spanCount)) {
                //最后一行，有边界需要完整分割线高度，没边界减掉
                top += if (hasBorder) -dividerHeight else 0
            } else {
                top -= dividerHeight / 2
            }
            bottom = if (position >= itemCount - spanCount && !hasBorder) {
                //最后几个，且没有边框
                top
            } else {
                top + dividerHeight
            }
            mVerticalDivider?.setBounds(left, top, right, bottom)
            mVerticalDivider?.draw(canvas)
            if (isFirstRow(position, spanCount) && hasBorder) {
                //第一行且有边界，需要最上面一条
                top = mBounds.top
                bottom = top + dividerHeight
                mVerticalDivider?.setBounds(left, top, right, bottom)
                mVerticalDivider?.draw(canvas)
            }
        }
        canvas.restore()
    }

    /**
     * 绘制垂直分割线
     */
    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        //总item数
        val itemCount = parent.adapter!!.itemCount
        //列数
        val spanCount = getSpanCount(parent)
        //每个item宽度
        val itemWidth =
            (parent.width - parent.paddingLeft - parent.paddingRight) / spanCount
        //分割线宽度
        val dividerWidth = mHorizontalDivider?.intrinsicWidth ?: 0
        //每个item中分割线占的宽度
        val itemDividerWidth =
            (spanCount + if (hasBorder) 1 else -1) * dividerWidth / spanCount
        var top: Int
        var bottom: Int
        var right: Int
        var left: Int
        var position: Int
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            parent.layoutManager!!.getDecoratedBoundsWithMargins(child, mBounds)
            position = parent.getChildAdapterPosition(child)
            //分割线上下坐标
            top = mBounds.top
            bottom = mBounds.bottom
            if (!hasBorder) {
                //没有边界时
                if (position + spanCount == itemCount) {
                    //最后一个item上面那个
                    bottom += mVerticalDivider?.intrinsicHeight ?: 0 / 2
                } else if (itemCount % spanCount != 0 && itemCount % spanCount < position % spanCount && position > itemCount - spanCount) {
                    //item不是整行数时，倒数第二行最后几个的下边界线需要减去
                    bottom -= mVerticalDivider?.intrinsicHeight ?: 0 / 2
                }
            }
            //行中位置
            val indexHorizontal = position % spanCount
            if (hasBorder) {
                //有边界
                left = mBounds.left - (itemDividerWidth - dividerWidth) * indexHorizontal
                right = left + dividerWidth
            } else {
                left =
                    mBounds.left - (dividerWidth - indexHorizontal * (dividerWidth - itemDividerWidth))
                //无边界且是第一列不绘制
                right = left + if (indexHorizontal == 0) 0 else dividerWidth
            }
            //画左边纵向分割线
            mVerticalDivider?.setBounds(left, top, right, bottom)
            mVerticalDivider?.draw(canvas)
            if (hasBorder && isLastColumn(position, spanCount, itemCount)) {
                //最后一列
                left = if ((indexHorizontal + 1) % spanCount == 0) {
                    //每行满格最后一个
                    parent.width - parent.paddingRight - dividerWidth
                } else {
                    //不满格的最后一个
                    left + itemWidth - (itemDividerWidth - dividerWidth)
                }
                right = left + dividerWidth
                mVerticalDivider?.setBounds(left, top, right, bottom)
                mVerticalDivider?.draw(canvas)
            }
        }
        canvas.restore()
    }

    /**
     * 第一行
     */
    private fun isFirstRow(position: Int, spanCount: Int): Boolean {
        return position < spanCount
    }

    /**
     * 最后一行
     */
    private fun isLastRow(position: Int, itemCount: Int, spanCount: Int): Boolean {
        //当前个数小于总个数
        //并且
        //item个数正好排成N行spanCount，且当前位置>=总item-每行个数
        //或
        //item个数多了一部分((item+1)%spanCount)，且当前位置在多出来部分
        return (position < itemCount
                && (itemCount % spanCount == 0 && position >= itemCount - spanCount
                || itemCount % spanCount >= itemCount - position))
    }

    /**
     * 最后一列
     */
    private fun isLastColumn(position: Int, spanCount: Int, itemCount: Int): Boolean {
        //每行最后一个或总item最后一个
        return (position + 1) % spanCount == 0 || position + 1 == itemCount
    }

    fun setVerticalDivider(verticalDivider: Drawable?) {
        mVerticalDivider = verticalDivider!!
    }

    fun setHorizontalDivider(horizontalDivider: Drawable?) {
        mHorizontalDivider = horizontalDivider!!
    }

    fun setHasBorder(hasBorder: Boolean) {
        this.hasBorder = hasBorder
    }
}