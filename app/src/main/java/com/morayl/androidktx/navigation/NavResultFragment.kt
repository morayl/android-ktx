package com.morayl.androidktx.navigation

import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.morayl.androidktx.ext.doOnDestroy
import com.morayl.androidktx.ext.observeNavResult
import com.morayl.androidktx.ext.popBackStack
import com.morayl.androidktx.parameter.SealedNavResult
import kotlinx.parcelize.Parcelize
import kotlin.reflect.KClass

/**
 * 結果を返すFragmentはこのinterfaceを実装してください
 * そうすることで、FragmentExt.ktのobserveNavResultと合わせて結果を渡すことができるようになります
 */
interface NavResultFragment<T> {
    private fun Fragment.setNavResult(
        result: T, @IdRes targetDestinationId: Int? = null, key: String = createKey(result)
    ) {
        setNavResultInternal(result, targetDestinationId, key)
    }

    fun Fragment.setNavResultAndPopUp(
        result: T,
        @IdRes targetDestinationId: Int? = null,
        popUpInclusive: Boolean = false,
        key: String? = null
    ) {
        if (!key.isNullOrEmpty()) {
            setNavResult(result, targetDestinationId, key)
        } else {
            setNavResult(result, targetDestinationId)
        }
        if (targetDestinationId != null) {
            popBackStack(targetDestinationId, popUpInclusive)
        } else {
            popBackStack()
        }
    }

    private fun Fragment.setNavResultToCurrent(result: T, key: String) {
        findNavController().currentBackStackEntry?.savedStateHandle?.set(key, result)
    }

    fun Fragment.popUpAndSetNavResult(result: T, @IdRes targetDestinationId: Int? = null, key: String? = null) {
        if (targetDestinationId != null) {
            popBackStack(targetDestinationId)
        } else {
            popBackStack()
        }
        viewLifecycleOwner.doOnDestroy {
            setNavResultToCurrent(result, key ?: createKey(result))
        }
    }

    companion object {
        private const val DEFAULT_KEY = "default_key"
        fun <T> createKey(result: T): String {
            return if (result is SealedNavResult) result.key ?: DEFAULT_KEY else createKey(requireNotNull(result)::class)
        }

        inline fun <reified T> createKey(): String {
            return createKey(T::class)
        }

        fun createKey(result: KClass<*>): String {
            return result.qualifiedName ?: DEFAULT_KEY
        }
    }
}

interface UnitNavResultFragment {
    fun Fragment.setUnitNavResultAndPopUp(
        key: String, @IdRes targetDestinationId: Int? = null
    ) {
        setNavResultInternal(ParcelableUnit, targetDestinationId, key)
        popBackStack()
    }
}

private fun <T> Fragment.setNavResultInternal(
    result: T, @IdRes targetDestinationId: Int? = null, key: String = NavResultFragment.createKey(result)
) {
    val entry = if (targetDestinationId != null) {
        findNavController().getBackStackEntry(targetDestinationId)
    } else {
        findNavController().previousBackStackEntry
    }
    entry?.savedStateHandle?.set(key, result)
}


@Parcelize
private object ParcelableUnit : Parcelable {
    override fun toString() = "ParcelableUnit"
}

/**
 * ただ単に通知のみを伝えたい場合に利用します
 */
fun Fragment.observeUnitNavResult(key: String, onEvent: () -> Unit) {
    observeNavResult<ParcelableUnit>(key) {
        onEvent()
    }
}