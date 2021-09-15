package com.zhixing.zuche.extensions

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.tabs.TabLayout
import java.lang.reflect.ParameterizedType
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * @author Dylan Cai
 * @link https://github.com/DylanCaiCoding/ViewBindingKTX
 */
inline fun <reified VB : ViewBinding> ComponentActivity.binding() = lazy {
    inflateBinding<VB>(layoutInflater).also {
        setContentView(it.root)
    }
}

inline fun <reified VB : ViewBinding> Fragment.binding() =
    FragmentBindingDelegate<VB> { requireView().bind() }

inline fun <reified VB : ViewBinding> Fragment.binding(method: Method) =
    FragmentBindingDelegate<VB> {
        if (method == Method.BIND) {
            requireView().bind()
        } else {
            inflateBinding(layoutInflater)
        }
    }

inline fun <reified VB : ViewBinding> Dialog.binding() = lazy {
    inflateBinding<VB>(layoutInflater).also { setContentView(it.root) }
}

inline fun <reified VB : ViewBinding> ViewGroup.binding(attachToParent: Boolean = true) = lazy {
    inflateBinding<VB>(
        LayoutInflater.from(context), if (attachToParent) this else null, attachToParent
    )
}

inline fun <reified VB : ViewBinding> TabLayout.Tab.setCustomView(onBindView: VB.() -> Unit) {
    customView = inflateBinding<VB>(LayoutInflater.from(parent!!.context)).apply(onBindView).root
}

inline fun <reified VB : ViewBinding> TabLayout.Tab.bindCustomView(onBindView: VB.() -> Unit) =
    customView?.bind<VB>()?.run(onBindView)

inline fun <reified VB : ViewBinding> TabLayout.Tab.bindCustomView(
    bind: (View) -> VB, onBindView: VB.() -> Unit
) = customView?.let { bind(it).run(onBindView) }

inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, layoutInflater) as VB

inline fun <reified VB : ViewBinding> inflateBinding(parent: ViewGroup) =
    inflateBinding<VB>(LayoutInflater.from(parent.context), parent, false)

inline fun <reified VB : ViewBinding> inflateBinding(
    layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean
) = VB::class.java.getMethod(
    "inflate",
    LayoutInflater::class.java,
    ViewGroup::class.java,
    Boolean::class.java
).invoke(null, layoutInflater, parent, attachToParent) as VB

inline fun <reified VB : ViewBinding> View.bind() =
    VB::class.java.getMethod("bind", View::class.java).invoke(null, this) as VB

inline fun Fragment.doOnDestroyView(crossinline block: () -> Unit) =
    viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroyView() {
            block.invoke()
        }
    })

enum class Method { BIND, INFLATE }

interface BindingLifecycleOwner {
    fun onDestroyViewBinding(destroyingBinding: ViewBinding)
}

class FragmentBindingDelegate<VB : ViewBinding>(private val block: () -> VB) :
    ReadOnlyProperty<Fragment, VB> {
    private var binding: VB? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (binding == null) {
            binding = block()
            thisRef.doOnDestroyView {
                if (thisRef is BindingLifecycleOwner) thisRef.onDestroyViewBinding(binding!!)
                binding = null
            }
        }
        return binding!!
    }
}

/**
 * base
 */

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(layoutInflater: LayoutInflater): VB =
    withGenericBindingClass<VB>(this) { clazz ->
        clazz.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB
    }

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    attachToParent: Boolean
): VB =
    withGenericBindingClass<VB>(this) { clazz ->
        clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
            .invoke(null, layoutInflater, parent, attachToParent) as VB
    }

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(parent: ViewGroup): VB =
    inflateBindingWithGeneric(LayoutInflater.from(parent.context), parent, false)

fun <VB : ViewBinding> Any.bindViewWithGeneric(view: View): VB =
    withGenericBindingClass<VB>(this) { clazz ->
        clazz.getMethod("bind", View::class.java).invoke(null, view) as VB
    }

private fun <VB : ViewBinding> withGenericBindingClass(any: Any, block: (Class<VB>) -> VB): VB {
    any.allParameterizedType.forEach { parameterizedType ->
        parameterizedType.actualTypeArguments.forEach {
            try {
                return block.invoke(it as Class<VB>)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    throw IllegalArgumentException("There is no generic of ViewBinding.")
}

private val Any.allParameterizedType: List<ParameterizedType>
    get() {
        val genericParameterizedType = mutableListOf<ParameterizedType>()
        var genericSuperclass = javaClass.genericSuperclass
        var superclass = javaClass.superclass
        while (superclass != null) {
            if (genericSuperclass is ParameterizedType) {
                genericParameterizedType.add(genericSuperclass)
            }
            genericSuperclass = superclass.genericSuperclass
            superclass = superclass.superclass
        }
        return genericParameterizedType
    }

/**
 * adapter
 */

inline fun <reified VB : ViewBinding> BindingViewHolder(parent: ViewGroup) =
    BindingViewHolder(inflateBinding<VB>(parent))

class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root) {
    constructor(parent: ViewGroup, inflate: (LayoutInflater, ViewGroup, Boolean) -> VB) :
            this(inflate(LayoutInflater.from(parent.context), parent, false))
}

inline fun <VB : ViewBinding> BindingViewHolder<VB>.onBinding(crossinline action: VB.(Int) -> Unit) =
    apply { binding.action(absoluteAdapterPosition) }

inline fun <VB : ViewBinding> BindingViewHolder<VB>.onItemClick(crossinline action: VB.(Int) -> Unit) =
    apply { itemView.setOnClickListener { binding.action(absoluteAdapterPosition) } }

inline fun <T, reified VB : ViewBinding> ListAdapter(
    diffCallback: DiffUtil.ItemCallback<T>,
    crossinline onBindViewHolder: BindingViewHolder<VB>.(T) -> Unit
) = object : ListAdapter<T, BindingViewHolder<VB>>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindingViewHolder<VB>(parent)

    override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
        onBindViewHolder(holder, currentList[position])
    }
}