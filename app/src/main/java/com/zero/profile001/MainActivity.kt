package com.zero.profile001

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import com.zero.profile001.ui.theme.Profile001Theme
import com.zero.profile001.ui.util.clickNoRipple
import com.zero.profile001.ui.util.px
import com.zero.profile001.ui.util.textPx
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Profile001Theme {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Background
        Image(
            painter = painterResource(R.mipmap.bg),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        UserCard()

        FeatureList()

        BottomBar()
    }
}

@Composable
fun BoxScope.BottomBar() {
    val navTabs = remember {
        listOf(
            R.mipmap.nav_home,
            R.mipmap.nav_notification,
            R.mipmap.nav_favorite,
            R.mipmap.nav_mine,
        )
    }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val indicatorWidth = 63.px()
    val shape = RoundedCornerShape(50.px())
    BoxWithConstraints(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(horizontal = 92.px())
            .padding(bottom = 73.px())
            .fillMaxWidth()
            .height(200.px())
            .shadow(
                elevation = 50.px(),
                shape = shape
            )
            .clip(shape)
            .background(Color.White)
    ) {
        val tabWidth = maxWidth / 4 // 每个TAB占据1/4
        val targetOffset by animateDpAsState(
            targetValue = tabWidth * selectedIndex + tabWidth / 2 - indicatorWidth / 2 // indicator 偏移目标
        )
//        Log.d("TAG", "BottomBar: $selectedIndex $targetOffset")
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            navTabs.fastForEachIndexed { index, tab ->
                TabButton(tab) {
                    selectedIndex = index // 搞错了
                }
            }
        }

        Image(
            painter = painterResource(R.mipmap.tab_indicator),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset { IntOffset(targetOffset.toPx().roundToInt(), 0) } // 指示器偏移位置
                .width(indicatorWidth)
                .height(8.px())
        )
    }
}

@Composable
fun RowScope.TabButton(tab: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickNoRipple(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(tab),
            contentDescription = null,
            modifier = Modifier.size(76.px())
        )
    }
}

@Composable
fun UserCard() {
    Row(
        modifier = Modifier
            .padding(horizontal = 92.px())
            .padding(top = 349.px())
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(90.px())
    ) {
        Image(
            painter = painterResource(R.mipmap.user_avatar),
            contentDescription = null,
            modifier = Modifier.size(278.px())
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.px())
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(16.px())
            ) {
                Text(
                    text = "机器小怪兽",
                    fontWeight = FontWeight.Bold,
                    fontSize = 60.textPx()
                )
                Image(
                    painter = painterResource(R.mipmap.icon_edit),
                    contentDescription = null,
                    modifier = Modifier.size(48.px())
                )
            }
            Text(
                text = "ID:2236985",
                fontSize = 42.textPx(),
                color = Color(0xFFA0A7BA)
            )
        }
    }
}

data class Feature(
    val icon: Int,
    val label: String
)

@Composable
fun FeatureList() {
    val features = remember {
        listOf(
            Feature(R.mipmap.icon_order, "我的预约"),
            Feature(R.mipmap.icon_doc, "病历窗口"),
            Feature(R.mipmap.icon_packet, "我的钱包"),
            Feature(R.mipmap.icon_security, "隐私政策"),
            Feature(R.mipmap.icon_question, "帮助"),
            Feature(R.mipmap.icon_setting, "设置"),
        )
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 92.px())
            .padding(top = 721.px())
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(80.px())
    ) {
        features.fastForEach { feature ->
            FeatureItem(feature)
        }
    }
}

@Composable
fun FeatureItem(feature: Feature) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(136.px())
            .clickNoRipple {
                Toast.makeText(context, feature.label, Toast.LENGTH_SHORT).show()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(38.px())
    ) {
        Image(
            painter = painterResource(feature.icon),
            contentDescription = null,
            modifier = Modifier.size(136.px())
        )
        Text(
            text = feature.label,
            fontSize = 46.textPx(),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(R.mipmap.icon_right),
            contentDescription = null,
            modifier = Modifier.size(48.px())
        )
    }
}