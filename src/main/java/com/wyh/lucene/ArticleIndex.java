package com.wyh.lucene;

import com.wyh.entity.Article;
import com.wyh.util.DateUtil;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

@Component("articleIndex")
public class ArticleIndex {

    private Directory dir = null;

    @Value("${lucenePath}")
    private String lucenePath;

    /**
     * 获取IndexWriter实例
     * @return
     * @throws Exception
     */
    public IndexWriter getWriter() throws Exception{
        dir = FSDirectory.open(Paths.get(lucenePath));
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

    /**
     * 添加帖子信息
     * @param article
     */
    public boolean addIndex(Article article){
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            IndexWriter writer = getWriter();
            Document doc = new Document();
            doc.add(new StringField("id", String.valueOf(article.getId()), Field.Store.YES));
            doc.add(new TextField("name", article.getName(),Field.Store.YES));
            doc.add(new StringField("publishDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
            doc.add(new TextField("content",article.getContent(),Field.Store.YES));
            writer.addDocument(doc);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            lock.unlock();
        }
        return true;
    }
}
