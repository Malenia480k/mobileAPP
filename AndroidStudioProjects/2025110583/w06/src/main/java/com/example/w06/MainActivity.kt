package com.example.w06

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.w06.ui.theme._2025110583주Theme
import kotlinx.coroutines.delay
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _2025110583주Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BubbleGameScreen()
                }
            }
        }
    }
}

data class Bubble(
    val id: Int,
    var position: Offset,
    val radius: Float,
    val color: Color,
    val creationTime: Long = System.currentTimeMillis(),
    val velocityX: Float = 0f,
    val velocityY: Float = 0f
)

// 게임 상태 data를 한군데 모아 편리하게 관리하는 클래스
class GameState(
    // 클래스를 생성할 때 초기 버블 리스트를 받을 수 있도록 파라미터 추가
    // 기본값으로 emptyList()를 지정하여, 파라미터 없이 GameState()로도 생성 가능
    initialBubbles: List<Bubble> = emptyList()
) {
    var bubbles by mutableStateOf(initialBubbles)
    var score by mutableStateOf(0)
    var isGameOver by mutableStateOf(false)
    var timeLeft by mutableStateOf(60) // 남은 시간: 60초로 시작
}
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BubbleGameScreen() {
    // 게임에 필요한 상태 변수들 선언
    // ✅ 1. GameState를 remember로 한 번만 생성
    val gameState = remember { GameState() }

    // TODO 2. 타이머 로직 추가
    LaunchedEffect(key1 = gameState.isGameOver) {
        // 게임이 진행 중일 때만 타이머 작동
        if (!gameState.isGameOver && gameState.timeLeft > 0) {
            while (true) {
                delay(1000L) // 1초 대기
                gameState.timeLeft-- // 시간 1초 감소
                if (gameState.timeLeft == 0) {
                    gameState.isGameOver = true // 시간이 0이 되면 게임 오버
                    break
                }
                // 3초가 지난 버블 제거
                val currentTime = System.currentTimeMillis()
                gameState.bubbles = gameState.bubbles.filter { // filter()는 원본 리스트를 변경하지 않고 새 리스트 생성
                    currentTime - it.creationTime < 3000
                }

            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // TODO 1. 상단 상태 바 UI 추가
        GameStatusRow(score = gameState.score, timeLeft = gameState.timeLeft)

        BoxWithConstraints {
            // 이 영역 안에서는 아래 4개 변수가 자동으로 제공됨
            // 👉 maxWidth, maxHeight, minWidth, minHeight

            // TODO: 3. 버블 생성 및 터치 로직 구현
            val newBubble = Bubble(
                id = Random.nextInt(),
                position = Offset(
                    x = Random.nextFloat(), //* maxWidth.value, // 위치 단위는 dp
                    y = Random.nextFloat() //* maxHeight.value
                ),
                radius = Random.nextFloat() * 50 + 50,
                color = Color(
                    red = Random.nextInt(256),
                    green = Random.nextInt(256),
                    blue = Random.nextInt(256),
                    alpha = 200
                )
            )
            BubbleComposable(bubble = newBubble) {
                gameState.score++;
            }

            // TODO: 1. 상단 상대 바 (점수, 시간) UI 구현
            // TODO: 2. 시간 타이머 로직 구현
            // TODO: 3. 버블 생성 및 터치 로직 구현
            // TODO: 4. 게임 오버 시 AlertDialog 표시
        }
    }
}

// 상단 UI를 별도의 Composable로 분리 (가독성 향상)
@Composable
fun GameStatusRow(score: Int, timeLeft: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Score: $score", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = "Time: ${timeLeft}s", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BubbleComposable(bubble: Bubble, onClick: () -> Unit) {
    Canvas(
        modifier = Modifier
            .size((bubble.radius * 2).dp)
            .offset(x = bubble.position.x.dp, y = bubble.position.y.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null, // 클릭 시 물결 효과 제거
                onClick = onClick
            )
    ) {
        // 3. 원은 Canvas의 정가운데에 그립니다.
        drawCircle(
            color = bubble.color,
            radius = size.width / 2, // / size.width는 Canvas의 실제 가로 픽셀(px) 크기
            center = center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BubbleGamePreview() {
    _2025110583주Theme{
        BubbleGameScreen()
    }
}



    fun makeNewBubble(maxWidth: Dp, maxHeight: Dp): Bubble {
        return Bubble(
            id = Random.nextInt(),
            position = Offset(
                x = Random.nextFloat() * maxWidth.value,
                y = Random.nextFloat() * maxHeight.value
            ),
            radius = Random.nextFloat() * 50 + 50,
            velocityX = Random.nextFloat() * 5,
            velocityY = Random.nextFloat() * 5,
            color = Color(
                red = Random.nextInt(256),
                green = Random.nextInt(256),
                blue = Random.nextInt(256),
                alpha = 200
            )
        )
    }
