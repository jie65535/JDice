package top.jie65535

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.utils.info
import kotlin.random.Random

object JDice : KotlinPlugin(
    JvmPluginDescription(
        id = "top.jie65535.dice",
        name = "J Dice",
        version = "0.1.0",
    ) {
        author("jie65535")
    }
) {
    private val regex = Regex("""\b(\d*)[dD](\d+)\b""")
    private val random = Random(System.currentTimeMillis())

    override fun onEnable() {
        GlobalEventChannel.parentScope(this)
            .subscribeMessages {
                regex.finding {
                    val (c, d) = it.destructured
                    val count = if (c.isEmpty()) 1 else c.toInt()
                    val top = d.toInt()
                    if (count > 0 && top > 1) {
                        val value = random.nextInt(count, count * top + 1)
                        subject.sendMessage(message.quote() + value.toString())
                    }
                }
            }

        logger.info { "Plugin loaded. https://github.com/jie65535/JDice" }
    }
}
