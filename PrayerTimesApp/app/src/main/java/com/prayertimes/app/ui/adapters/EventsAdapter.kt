package com.prayertimes.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prayertimes.app.data.models.IslamicEvent
import com.prayertimes.app.databinding.ItemEventBinding

class EventsAdapter(
    private val onEventClick: (IslamicEvent) -> Unit
) : ListAdapter<IslamicEvent, EventsAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EventViewHolder(
        private val binding: ItemEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: IslamicEvent) {
            binding.apply {
                textEventTitle.text = event.title
                textEventDescription.text = event.description
                textEventType.text = event.eventType.name.replace("_", " ")
                
                // Show important indicator
                imageImportant.visibility = if (event.isImportant) {
                    android.view.View.VISIBLE
                } else {
                    android.view.View.GONE
                }
                
                // Show recurring indicator
                imageRecurring.visibility = if (event.isRecurring) {
                    android.view.View.VISIBLE
                } else {
                    android.view.View.GONE
                }
                
                root.setOnClickListener {
                    onEventClick(event)
                }
            }
        }
    }

    private class EventDiffCallback : DiffUtil.ItemCallback<IslamicEvent>() {
        override fun areItemsTheSame(oldItem: IslamicEvent, newItem: IslamicEvent): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: IslamicEvent, newItem: IslamicEvent): Boolean {
            return oldItem == newItem
        }
    }
}
