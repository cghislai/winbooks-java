package be.valuya.winbooks.api.extra;

import be.valuya.winbooks.domain.error.WinbooksError;
import be.valuya.winbooks.domain.error.WinbooksException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.iryndin.jdbf.core.DbfRecord;
import net.iryndin.jdbf.reader.DbfReader;

/**
 *
 * @author Yannick
 */
public class DbfUtils {

    public static Stream<DbfRecord> streamDbf(InputStream inputStream, Charset charset) {
        try {
            DbfReader dbfReader = new DbfReader(inputStream);
            return streamDbf(dbfReader, charset)
                    .onClose(() -> closeDbfReader(dbfReader));
        } catch (IOException exception) {
            throw new DbfException("DBF read error", exception);
        }
    }

    public static void closeDbfReader(DbfReader dbfReader) {
        try {
            dbfReader.close();
        } catch (IOException exception) {
            throw new DbfException("DBF close error", exception);
        }
    }

    public static Stream<DbfRecord> streamDbf(DbfReader dbfReader, Charset charset) {
        Spliterator<DbfRecord> spliterator = new DbfSpliterator(dbfReader, charset);
        return StreamSupport.stream(spliterator, false);
    }

    private static class DbfSpliterator implements Spliterator<DbfRecord> {

        private final DbfReader dbfReader;
        private final Charset charset;

        public DbfSpliterator(DbfReader dbfReader, Charset charset) {
            this.dbfReader = dbfReader;
            this.charset = charset;
        }

        @Override
        public boolean tryAdvance(Consumer<? super DbfRecord> consumer) {
            try {
                DbfRecord dbfRecord = dbfReader.read();
                if (dbfRecord == null) {
                    return false;
                }
                dbfRecord.setStringCharset(charset);
                consumer.accept(dbfRecord);
                return true;
            } catch (IOException exception) {
                throw new WinbooksException(WinbooksError.UNKNOWN_ERROR, exception);
            }
        }

        @Override
        public Spliterator<DbfRecord> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return Long.MAX_VALUE;
        }

        @Override
        public int characteristics() {
            return Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE;
        }
    }

}
