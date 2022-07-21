package de.mkammerer.vmbackup.progress;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DataSizeTest {
    @Test
    void teraBytes() {
        DataSize dataSize = DataSize.ofBytes(2781764417536L);
        assertThat(dataSize.toString()).isEqualTo("2.53 TiB");
    }

    @Test
    void teraBytesExact() {
		DataSize dataSize = DataSize.ofBytes(1099511627776L);
        assertThat(dataSize.toString()).isEqualTo("1.00 TiB");
    }

    @Test
    void gigaBytes() {
		DataSize dataSize = DataSize.ofBytes(2716566814L);
        assertThat(dataSize.toString()).isEqualTo("2.53 GiB");
    }

    @Test
    void gigaBytesExact() {
		DataSize dataSize = DataSize.ofBytes(1073741824L);
        assertThat(dataSize.toString()).isEqualTo("1.00 GiB");
    }

    @Test
    void megaBytes() {
		DataSize dataSize = DataSize.ofBytes(2652897L);
        assertThat(dataSize.toString()).isEqualTo("2.53 MiB");
    }

    @Test
    void megaBytesExact() {
		DataSize dataSize = DataSize.ofBytes(1048576L);
        assertThat(dataSize.toString()).isEqualTo("1.00 MiB");
    }

    @Test
    void kiloBytes() {
		DataSize dataSize = DataSize.ofBytes(2590L);
        assertThat(dataSize.toString()).isEqualTo("2.53 KiB");
    }

    @Test
    void kiloBytesExact() {
		DataSize dataSize = DataSize.ofBytes(1024L);
        assertThat(dataSize.toString()).isEqualTo("1.00 KiB");
    }

    @Test
	void bytes() {
		DataSize dataSize = DataSize.ofBytes(1023L);
		assertThat(dataSize.toString()).isEqualTo("1023 B");
	}

	@Test
	void doesNotAcceptNegativeBytes() {
		assertThatThrownBy(() -> DataSize.ofBytes(-1)).isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("'bytes'").hasMessageContaining("was -1");
	}

	@Test
	void parse() {
		assertThat(DataSize.parse("1023")).isEqualTo(DataSize.ofBytes(1023));
		assertThat(DataSize.parse("1KiB")).isEqualTo(DataSize.ofKiloBytes(1));
		assertThat(DataSize.parse("1MiB")).isEqualTo(DataSize.ofMegaBytes(1));
		assertThat(DataSize.parse("1GiB")).isEqualTo(DataSize.ofGigaBytes(1));
		assertThat(DataSize.parse("1TiB")).isEqualTo(DataSize.ofTeraBytes(1));
	}

	@Test
	void of() {
		assertThat(DataSize.ofBytes(1023)).isEqualTo(new DataSize(1023L));
		assertThat(DataSize.ofKiloBytes(1)).isEqualTo(new DataSize(1024L));
		assertThat(DataSize.ofMegaBytes(1)).isEqualTo(new DataSize(1048576L));
		assertThat(DataSize.ofGigaBytes(1)).isEqualTo(new DataSize(1073741824L));
		assertThat(DataSize.ofTeraBytes(1)).isEqualTo(new DataSize(1099511627776L));
	}
}