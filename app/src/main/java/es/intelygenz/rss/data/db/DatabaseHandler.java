package es.intelygenz.rss.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import es.intelygenz.rss.data.entity.response.entity.Article;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION   = 1;
    private static final String DATABASE_NAME   = "articlesManager";
    private static final String TABLE_ARTICLES  = "articles";

    private static final String KEY_AUTHOR          = "author";
    private static final String KEY_TITLE           = "name";
    private static final String KEY_DESCRIPTION     = "decription";
    private static final String KEY_URL             = "url";
    private static final String KEY_URL_TO_IMAGE    = "urlToImage";
    private static final String KEY_PUBLISHED_AT    = "publishedAt";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ARTICLES + "("
                + KEY_AUTHOR + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_URL + " TEXT,"
                + KEY_URL_TO_IMAGE + " TEXT,"
                + KEY_PUBLISHED_AT + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        onCreate(db);
    }

    public void saveArticleList(List<Article> articleList) {
        SQLiteDatabase db = this.getWritableDatabase();

        for(Article article : articleList) {
            ContentValues values = getArticleContentValues(article);
            db.insert(TABLE_ARTICLES, null, values);
        }

        db.close();
    }

    public ContentValues getArticleContentValues(Article article) {
        ContentValues values = new ContentValues();
        values.put(KEY_AUTHOR, article.getAuthor());
        values.put(KEY_TITLE, article.getTitle());
        values.put(KEY_DESCRIPTION, article.getDescription());
        values.put(KEY_URL, article.getUrl());
        values.put(KEY_URL_TO_IMAGE, article.getUrlToImage());
        values.put(KEY_PUBLISHED_AT, article.getPublishedAt());

        return  values;
    }

    public List<Article> getLastContacts(int count) {
        List<Article> articleList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ARTICLES + " limit " + count;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Article article = new Article();
                article.setAuthor(cursor.getString(0));
                article.setTitle(cursor.getString(1));
                article.setDescription(cursor.getString(2));
                article.setUrl(cursor.getString(3));
                article.setUrlToImage(cursor.getString(4));
                article.setPublishedAt(cursor.getString(5));
                articleList.add(article);
            } while (cursor.moveToNext());
        }

        return articleList;
    }
}
