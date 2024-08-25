package com.example.productservice.repositories.elasticsearch;

import com.example.productservice.modals.ProductDocument;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, String> {
    ProductDocument save(ProductDocument productDocument);
    Page<ProductDocument> findAllByTitleContaining(String name, Pageable pageable);
}

