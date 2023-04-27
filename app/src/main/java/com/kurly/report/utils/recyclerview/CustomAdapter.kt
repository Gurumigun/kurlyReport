package com.kurly.report.utils.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kurly.report.BR
import com.kurly.report.utils.findLifecycleOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */

open class CustomPagingAdapter<ITEM : RecyclerViewBindingModel> : PagingDataAdapter<ITEM, CustomViewHolder>, PagingAdapterInterface<ITEM> {
    constructor(
        diffCallback: DiffUtil.ItemCallback<ITEM>,
        mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
        workerDispatcher: CoroutineDispatcher = Dispatchers.Default
    ) : super(diffCallback, mainDispatcher, workerDispatcher)

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        getItem(position)?.let { holder.onBindViewHolder(it, position, null) } ?: throw RuntimeException("item is Null")
    }

    override fun onBindViewHolder(
        holder: CustomViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        getItem(position)?.let { holder.onBindViewHolder(it, position, payloads) } ?: throw RuntimeException("item is Null")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder = CustomViewHolder(viewType, parent = parent, null)

    override fun onViewAttachedToWindow(holder: CustomViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: CustomViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }

    override fun getItemViewType(position: Int): Int = getItem(position)?.layoutId() ?: throw RuntimeException("getItemViewType is Null")
}

open class CustomListAdapter<ITEM : RecyclerViewBindingModel>(
    diffCallBack: DiffUtil.ItemCallback<ITEM>,
    private val lifecycleOwner: LifecycleOwner? = null
) : ListAdapter<ITEM, CustomViewHolder>(diffCallBack), ListAdapterInterface<ITEM> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(viewType, parent = parent, lifecycleOwner)

    override fun onBindViewHolder(
        holder: CustomViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.onBindViewHolder(getItem(position), position, payloads)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBindViewHolder(getItem(position), position, null)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId()
    }

    override fun onViewAttachedToWindow(holder: CustomViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: CustomViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }

    override fun getItemId(position: Int): Long = currentList[position].itemId() ?: super.getItemId(position)

    override fun getCurrentItems(): List<ITEM> = currentList

    override fun getItemCount(): Int = currentList.size

}

open class CustomViewHolder(
    @LayoutRes layoutResId: Int,
    parent: ViewGroup?,
    private val lifecycleOwner: LifecycleOwner? = null,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent?.context)
        .inflate(layoutResId, parent, false)
), LifecycleOwner {
    private val lifeCycleOwner: LifecycleOwner? = this.lifecycleOwner ?: itemView.findLifecycleOwner()

    private val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!

    private val lifecycleRegistry =  LifecycleRegistry(this)

    init {
        binding.lifecycleOwner = this
        lifeCycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                if (lifecycleRegistry.currentState !=  Lifecycle.State.INITIALIZED) {
                    lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
                }
            }
        })
    }

    open fun onBindViewHolder(item: RecyclerViewBindingModel, position: Int, payLoad: List<Any>?) {
        with(binding) {
            item.bindingVariableId()?.let {
                setVariable(it, item)
            }
            setVariable(BR.itemPosition, position)
            if (item.requireExecutePendingBindings()) {
                executePendingBindings()
            }
        }
    }

    fun onAttach() {
        if (lifecycleRegistry.currentState != Lifecycle.State.DESTROYED) {
            lifecycleRegistry.currentState = Lifecycle.State.STARTED
            lifecycleRegistry.currentState = Lifecycle.State.RESUMED
        }
    }

    fun onDetach() {
        if (lifecycleRegistry.currentState != Lifecycle.State.DESTROYED) {
            lifecycleRegistry.currentState = Lifecycle.State.CREATED
        }
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry
}

