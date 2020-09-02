package com.github.fabriciolfj.dataadvanced.domain.repository;


import com.github.fabriciolfj.dataadvanced.domain.components.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.Dialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

@Slf4j
@Transactional(readOnly = true)
public class BatchRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BatchRepository<T, ID> {

    private final EntityManager entityManager;

    public BatchRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public <S extends T> void saveInBatch(List<S> entities) {

        if (entities == null) {
            throw new IllegalArgumentException("The given Iterable of entities cannot be null!");
        }

        int i = 0;

        BatchExecutor batchExecutor = SpringContext.getBean(BatchExecutor.class);
        try {
            batchExecutor.saveInBatch(entities);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            // log exception
        } catch (ExecutionException ex) {
            // log exception
        }

        /*for (S entity : entities) {
            if (i % batchSize() == 0 && i > 0) {
                log.info("Flushing the entitymanager containing {} entities...", batchSize());
                entityManager.flush();
                entityManager.clear();
            }
            entityManager.persist(entity);
            i++;
        }


        if (i > 0) {
            log.info("Flushing the remaining entities ..." + i);
            entityManager.flush();
            entityManager.clear();
        }*/
    }

    /*private static int batchSize() {

        int batchsize = Integer.valueOf(Dialect.DEFAULT_BATCH_SIZE); // default batch size

        Properties configuration = new Properties();
        try ( InputStream inputStream = BatchRepositoryImpl.class.getClassLoader()
                .getResourceAsStream("application.yml")) {
            configuration.load(inputStream);
        } catch (IOException ex) {
            log.error("Cannot fetch batch size. Using further Dialect.DEFAULT_BATCH_SIZE{0}", ex);
            return batchsize;
        }

        String batchsizestr = configuration.getProperty(
                "spring.jpa.properties.hibernate.jdbc.batch_size");
        if (batchsizestr != null) {
            batchsize = Integer.valueOf(batchsizestr);
        }

        return batchsize;
    }*/
}
