package com.example.gptbros.model

import java.util.Date
import java.util.UUID

data class FolderListItem(
    val sessionId : UUID,
    val SummaryName : String,
    val className : String,
    val Stage: Stage,
    val Status: Status,
    val date: Date,
) {
    //THIS WILL CAUSE BUGS, IN THE LONG TERM FIND A BETTER WAY TO EXPRESS AN EMPTY FOLDER ITEM
    companion object{
        fun emptyFolderListItem () : FolderListItem{
            return FolderListItem(UUID.randomUUID(), "", "", Stage.SUMMARIZING, Status.ERROR, Date())
        }
    }
}
