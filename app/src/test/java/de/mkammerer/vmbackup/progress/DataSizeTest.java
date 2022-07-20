package de.mkammerer.vmbackup.progress;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DataSizeTest {
    @Test
    void teraBytes() {
        DataSize dataSize = DataSize.of(2781764417536L);
        assertThat(dataSize.toString()).isEqualTo("2.53 TiB");
    }

    @Test
    void teraBytesExact() {
        DataSize dataSize = DataSize.of(1099511627776L);
        assertThat(dataSize.toString()).isEqualTo("1.00 TiB");
    }

    @Test
    void gigaBytes() {
        DataSize dataSize = DataSize.of(2716566814L);
        assertThat(dataSize.toString()).isEqualTo("2.53 GiB");
    }

    @Test
    void gigaBytesExact() {
        DataSize dataSize = DataSize.of(1073741824L);
        assertThat(dataSize.toString()).isEqualTo("1.00 GiB");
    }

    @Test
    void megaBytes() {
        DataSize dataSize = DataSize.of(2652897L);
        assertThat(dataSize.toString()).isEqualTo("2.53 MiB");
    }

    @Test
    void megaBytesExact() {
        DataSize dataSize = DataSize.of(1048576L);
        assertThat(dataSize.toString()).isEqualTo("1.00 MiB");
    }

    @Test
    void kiloBytes() {
        DataSize dataSize = DataSize.of(2590L);
        assertThat(dataSize.toString()).isEqualTo("2.53 KiB");
    }

    @Test
    void kiloBytesExact() {
        DataSize dataSize = DataSize.of(1024L);
        assertThat(dataSize.toString()).isEqualTo("1.00 KiB");
    }

    @Test
    void bytes() {
        DataSize dataSize = DataSize.of(1023L);
        assertThat(dataSize.toString()).isEqualTo("1023 B");
    }

    @Test
    void doesNotAcceptNegativeBytes() {
        assertThatThrownBy(() -> DataSize.of(-1)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("'bytes'").hasMessageContaining("was -1");
    }
}