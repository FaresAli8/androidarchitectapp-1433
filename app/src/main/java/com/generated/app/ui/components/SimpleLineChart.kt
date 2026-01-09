package com.generated.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun SimpleLineChart(
    dataPoints: List<Int>,
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.primary
) {
    if (dataPoints.isEmpty()) return

    Box(modifier = modifier.height(200.dp).padding(16.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val spacing = if (dataPoints.size > 1) width / (dataPoints.size - 1) else 0f
            val maxData = dataPoints.maxOrNull() ?: 1
            val minData = 0 

            // Normalize coordinates
            val coordinates = dataPoints.mapIndexed { index, value ->
                val x = index * spacing
                val y = height - ((value - minData).toFloat() / (maxData - minData) * height)
                Offset(x, y)
            }

            // Draw line
            val path = Path().apply {
                if (coordinates.isNotEmpty()) {
                    moveTo(coordinates.first().x, coordinates.first().y)
                    coordinates.drop(1).forEach { 
                        lineTo(it.x, it.y)
                    }
                }
            }

            drawPath(
                path = path,
                color = lineColor,
                style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round)
            )

            // Draw Points
            coordinates.forEach { point ->
                drawCircle(
                    color = Color.White,
                    radius = 6.dp.toPx(),
                    center = point
                )
                drawCircle(
                    color = lineColor,
                    radius = 4.dp.toPx(),
                    center = point
                )
            }
        }
    }
}