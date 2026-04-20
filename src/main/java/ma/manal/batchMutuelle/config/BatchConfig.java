package ma.manal.batchMutuelle.config;

import ma.manal.batchMutuelle.model.RembAssure;
import ma.manal.batchMutuelle.model.DossierMutuelle;
import ma.manal.batchMutuelle.processor.CompositeItemProcessor;
import ma.manal.batchMutuelle.repository.RembAssureRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private RembAssureRepository rembAssureRepository;
    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;

    public BatchConfig(RembAssureRepository rembAssureRepository,
                       JobRepository jobRepository,
                       PlatformTransactionManager transactionManager
                       ) {
        this.rembAssureRepository = rembAssureRepository;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }


    @Bean
    public Step step(){
         return new StepBuilder("step",jobRepository)
                 .<DossierMutuelle, RembAssure>chunk(10,transactionManager)
                 .reader(jsonReader())
                 .processor(compositeItemProcessor())
                 .writer(writer())
                 .faultTolerant()
                     .retryLimit(4)
                     .retry(ItemReaderException.class)
                     .retry(ItemWriterException.class)
                     .skipLimit(10)
                     .skip(IllegalArgumentException.class)
                     .skip(RuntimeException.class)
                 .build();
    }

    @Bean
    public Step clearDatabaseStep() {
        return new StepBuilder("clearDatabaseStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    rembAssureRepository.deleteAll();  // Clear the database
                    System.out.println("Database cleared!");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Job job(){
        return new JobBuilder("job",jobRepository)
                .start(clearDatabaseStep())
                .next(step())
                .build();
    }


    @Bean
    public CompositeItemProcessor compositeItemProcessor() {
        return new CompositeItemProcessor();
    }


    @Bean
    public RepositoryItemWriter<RembAssure> writer() {
        RepositoryItemWriter<RembAssure> writer = new RepositoryItemWriter<>();
        writer.setRepository(rembAssureRepository);
        writer.setMethodName("save");
        return writer;
    }
    @Bean
    public ItemReader<DossierMutuelle> jsonReader() {
        return new JsonItemReaderBuilder<DossierMutuelle>()
                .name("dossierMutuelle")
                .jsonObjectReader(new JacksonJsonObjectReader<>(DossierMutuelle.class))
                .resource(new FileSystemResource("src/main/resources/dossiers.json"))
                .build();
    }

}
