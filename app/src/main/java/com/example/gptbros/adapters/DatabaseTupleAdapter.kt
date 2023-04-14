package com.example.gptbros.adapters

import com.example.gptbros.model.*
import com.example.gptbros.model.FolderListItem
import com.example.gptbros.model.Session
import com.example.gptbros.model.Status


class DatabaseTupleAdapter {
    companion object {
        fun convertSessionSummaryToFolderListItem(session: Session, status: Status) : FolderListItem {
            return FolderListItem(session.sessionId, session.label, session.className, session.stage, status, session.date)
        }
    }
}