package com.example.Spring4Shell.security;

import java.security.KeyPair;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class Authorization {	
	public static KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.ES256);
}
