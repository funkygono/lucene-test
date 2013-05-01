package org.funky.repository.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.funky.repository")
public class Application {

    public static final Version LUCENE_VERSION = Version.LUCENE_42;

    @Bean
    public IndexWriterConfig indexWriterConfig() {
        return new IndexWriterConfig(LUCENE_VERSION, analyzer());
    }

    @Bean(destroyMethod = "close")
    public Analyzer analyzer() {
        return new StandardAnalyzer(LUCENE_VERSION);
    }

    @Bean(destroyMethod = "close")
    public Directory directory() {
        return new RAMDirectory();
    }

}
