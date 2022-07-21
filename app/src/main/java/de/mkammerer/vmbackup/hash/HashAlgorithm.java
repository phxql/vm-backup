package de.mkammerer.vmbackup.hash;

public enum HashAlgorithm {
	SHA1("SHA-1"),
	SHA224("SHA-224"),
	SHA256("SHA-256"),
	SHA384("SHA-384"),
	SHA512("SHA-512");

	private final String id;

	HashAlgorithm(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
