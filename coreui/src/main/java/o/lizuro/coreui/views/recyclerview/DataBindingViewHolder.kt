package o.lizuro.coreui.views.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DataBindingViewHolder<T>(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(itemId: Int, item: T) {
        binding.setVariable(itemId, item)
        binding.executePendingBindings()
    }

    fun cleanUp() {
        binding.unbind()
    }
}