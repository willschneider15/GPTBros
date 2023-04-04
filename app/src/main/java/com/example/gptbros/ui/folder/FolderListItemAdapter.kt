package com.example.gptbros.ui.folder
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gptbros.R
import com.example.gptbros.model.FolderListItem
import com.example.gptbros.model.GptBrosRepository
import java.text.SimpleDateFormat
import java.util.*

class FolderListItemAdapter(
    private val context: Context,
    private val folderListItems: List<FolderListItem>,
    private val updateDB : (sessionUUID : UUID) -> Unit
    private val onEditLabel : (sessionId: UUID) -> Unit
) : RecyclerView.Adapter<FolderListItemAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.lecture_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val folderListItem = folderListItems[position]

        holder.className.text = folderListItem.SummaryName
        holder.recordedDate.text = formatDate(folderListItem.date)
        holder.editButton.setOnClickListener {
            onEditLabel(folderListItems[position].sessionId)
        }

        holder.deleteButton.setOnClickListener {
            updateDB(folderListItems[position].sessionId)
        }
    }

    override fun getItemCount(): Int {
        return folderListItems.size
    }


    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(date)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val className: TextView = itemView.findViewById(R.id.class_name)
        val recordedDate: TextView = itemView.findViewById(R.id.recorded_date)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
        val editButton: ImageButton = itemView.findViewById(R.id.edit_button)
    }
}