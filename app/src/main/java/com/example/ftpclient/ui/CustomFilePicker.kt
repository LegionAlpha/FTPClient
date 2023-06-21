package com.example.ftpclient.ui

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.zaphlabs.filechooser.KnotFileChooser
import com.zaphlabs.filechooser.utils.FileType
import java.io.File

class CustomFilePicker {
    private var fileChooser: KnotFileChooser? = null

    constructor(context: Context, params: Params) {
        fileChooser = KnotFileChooser(
            context,
            allowBrowsing = params.allowBrowsing, // Allow User Browsing
            allowCreateFolder = params.allowCreateFolder, // Allow User to create Folder
            allowMultipleFiles = params.minimumFiles > 1, // Allow User to Select Multiple Files
            allowSelectFolder = params.allowSelectFolder, // Allow User to Select Folder
            minSelectedFiles = params.minimumFiles, // Allow User to Selec Minimum Files Selected
            maxSelectedFiles = params.maximumFiles, // Allow User to Selec Minimum Files Selected
            showFiles = params.showFiles, // Show Files or Show Folder Only
            showFoldersFirst = params.foldersFirst, // Show Folders First or Only Files
            showFolders = params.showFolders, //Show Folders
            showHiddenFiles = params.showHiddenFiles, // Show System Hidden Files
            initialFolder = params.initialFolder, //Initial Folder
            restoreFolder = false, //Restore Folder After Adding
            cancelable = true //Dismiss Dialog On Cancel (Optional)
        )

        fileChooser?.apply {
            title(params.title)
            onSelectedFilesListener {
                params.onFileSelected?.invoke(it)
            }
            onSelectedFileUriListener {
                params.onUriSelected?.invoke(it)
            }
        }
    }

    fun show() {
        fileChooser?.show()
    }

    class Builder {
        private var params = Params()

        fun setOnFileSelected(onFileSelected: OnFileSelected = null): Builder {
            params.onFileSelected = onFileSelected
            return this
        }

        fun setOnUriSelected(onUriSelected: OnUriSelected = null): Builder {
            params.onUriSelected = onUriSelected
            return this
        }

        fun setInitialFolder(initialFolder: File): Builder {
            params.initialFolder = initialFolder
            return this
        }

        fun setFilesLimit(min: Int, max: Int): Builder {
            params.minimumFiles = min
            params.maximumFiles = max
            return this
        }

        fun setTitle(title: String): Builder {
            params.title = title
            return this
        }

        fun setAllowSelectFolder(allow: Boolean): Builder {
            params.allowSelectFolder = allow
            return this
        }

        fun setAllowCreateFolder(allow: Boolean): Builder {
            params.allowCreateFolder = allow
            return this
        }

        fun setShowHiddenFiles(show: Boolean): Builder {
            params.showHiddenFiles = show
            return this
        }

        fun setShowFiles(show: Boolean): Builder {
            params.showFiles = show
            return this
        }

        fun setShowFolders(show: Boolean): Builder {
            params.showFolders = show
            return this
        }

        fun setFoldersFirst(foldersFirst: Boolean): Builder {
            params.foldersFirst = foldersFirst
            return this
        }

        fun setAllowBrowsing(allowBrowsing: Boolean): Builder {
            params.allowBrowsing = allowBrowsing
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            params.cancelable = cancelable
            return this
        }

        fun setFileType(fileType: FileType): Builder {
            params.fileType = fileType
            return this
        }

        fun build(context: Context): CustomFilePicker {
            return CustomFilePicker(context, params)
        }
    }


    class Params {
        var title = "Choose File"
        var initialFolder: File = Environment.getRootDirectory()
        var onFileSelected: OnFileSelected = null
        var onUriSelected: OnUriSelected = null
        var showFiles: Boolean = true
        var showFolders: Boolean = true
        var allowBrowsing: Boolean = true
        var allowSelectFolder: Boolean = true
        var allowCreateFolder: Boolean = true
        var foldersFirst: Boolean = true
        var showHiddenFiles: Boolean = true
        var minimumFiles: Int = 0
        var maximumFiles: Int = Int.MAX_VALUE
        var fileType: FileType = FileType.ALL
        var cancelable: Boolean = true
    }
}

typealias OnFileSelected = ((List<File>?) -> Unit)?
typealias OnUriSelected = ((List<Uri>?) -> Unit)?