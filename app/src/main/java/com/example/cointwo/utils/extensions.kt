import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.NumberFormat

fun LifecycleOwner.repeatCollectOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}


fun NumberFormat.operatorPrefix(num: Double): String {
    return when {
        num > 0 -> "+"
        else -> ""
    } + format(num)
}