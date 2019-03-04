package com.example.andrea.lab7

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils

import java.lang.IllegalArgumentException
import java.util.*

class DataBase: ContentProvider() {
    override fun onCreate(): Boolean {
        val context = context
        val dbHelper = DatabaseHelper(context)
        dab = dbHelper.writableDatabase
        return dab != null
    }
    private var dab: SQLiteDatabase? = null

    companion object {

        private val CONTACT_PROJECTION_MAP: HashMap<String, String>? = null
        internal val uriMatcher: UriMatcher
        internal val CON = 2
        internal val CON_ID = 1

        internal val DB_NAME = "com.example.andrea.Lab7.DataBase"
        internal val URL = "content://$DB_NAME/contacts"
        internal val db_URI = Uri.parse(URL)
        internal val ID = "_id"
        internal val NOMBRE = "nombre"

        internal val CEL = "cel"
        internal val EMAIL = "correo"




        init {
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher.addURI(DB_NAME, "contacts", CON)
            uriMatcher.addURI(DB_NAME, "contacts/#", CON_ID)
        }

        internal val BASE_NAME = "Contacts"
        internal val CONTACTS_TABLE_NAME = "contactos"
        internal val BASE_VERSION = 1
        internal val TABLE =
            " CREATE TABLE " + CONTACTS_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT NOT NULL, " + "tel TEXT NOT NULL," + "mail TEXT NOT NULL);"

    }


    private class DatabaseHelper internal constructor(context: Context) :
        SQLiteOpenHelper(context, BASE_NAME, null, BASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL(" DROP TABLE IF EXISTS $CONTACTS_TABLE_NAME")
            onCreate(db)
        }
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val rowId = dab!!.insert(CONTACTS_TABLE_NAME, "", values)

        if (rowId > 0) {
            val _uri = ContentUris.withAppendedId(db_URI, rowId)
            context!!.contentResolver.notifyChange(_uri, null)
            return _uri
        }
        throw SQLException("Failed to add a record into $uri")
    }






    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var count = 0
        when (uriMatcher.match(uri)) {
            CON -> count = dab!!.delete(CONTACTS_TABLE_NAME, selection, selectionArgs)

            CON_ID -> {
                val id = uri.pathSegments[1]
                count = dab!!.delete(
                    CONTACTS_TABLE_NAME, ID + " = " + id +
                            if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "", selectionArgs
                )
            }
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }

        context!!.contentResolver.notifyChange(uri, null)
        return count
    }
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        var counter = 0
        when (uriMatcher.match(uri)) {
            CON -> counter = dab!!.update(CONTACTS_TABLE_NAME, values, selection, selectionArgs)
            CON -> {
                counter = dab!!.update(
                    CONTACTS_TABLE_NAME, values, ID + "=" + uri.pathSegments[1] +
                            if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "", selectionArgs
                )
            }
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return counter
    }
    override fun getType(uri: Uri): String? {
        when (uriMatcher.match(uri)) {
            CON -> return "vnd.android.cursor.dir/vnd.example.contacts"
            CON_ID -> return "vnd.android.cursor.item/vnd.example.contacts"
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
    }
    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val qb = SQLiteQueryBuilder()
        qb.tables = CONTACTS_TABLE_NAME
        when (uriMatcher.match(uri)) {
            CON -> qb.setProjectionMap(CONTACT_PROJECTION_MAP)
            CON_ID -> qb.appendWhere(ID + "=" + uri.pathSegments[1])
        }
        val c = qb.query(
            dab, projection, selection, selectionArgs, null, null, sortOrder ?: NOMBRE
        )
        c.setNotificationUri(context!!.contentResolver, uri)
        return c
    }


}