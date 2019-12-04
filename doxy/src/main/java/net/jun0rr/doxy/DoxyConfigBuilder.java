/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import net.jun0rr.doxy.impl.TypedProperties;


/**
 *
 * @author Juno
 */
public class DoxyConfigBuilder {
  
  public static final String LOCALHOST = "localhost";
  
  public static final String DEFAULT_SERVER_NAME = "Doxy-0.1-SNAPSHOT";
  
  public static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";
  
  public static final String DEFAULT_CRYPT_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
  
  public static final int DEFAULT_HOST_PORT = 3333;
  
  public static final int DEFAULT_SERVER_PORT = 443;
  
  public static final int DEFAULT_REMOTE_PORT = 6060;
  
  public static final int DEFAULT_PROXY_PORT = 40080;
  
  public static final int DEFAULT_BUFFER_SIZE = 8*1024;
  
  public static final long DEFAULT_TIMEOUT = 8000;
  
  public static final int DEFAULT_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
  
  
  private final HostConfig host;
  
  private final HostConfig server;
  
  private final HostConfig remote;
  
  private final HostConfig proxy;
  
  private final String proxyUser;
  
  private final char[] proxyPass;
  
  private final Path pkpath;
  
  private final Path pubpath;
  
  private final Path kspath;
  
  private final char[] kspass;
  
  private final int threadPoolSize;
  
  private final int bufferSize;
  
  private final long timeout;
  
  private final boolean directBuffer;
  
  private final String serverName;
  
  private final String userAgent;
  
  private final String cryptAlg;
  
  public DoxyConfigBuilder() {
    this.host = HostConfig.of(LOCALHOST, DEFAULT_HOST_PORT);
    this.server = HostConfig.of(LOCALHOST, DEFAULT_SERVER_PORT);
    this.remote = HostConfig.of(LOCALHOST, DEFAULT_REMOTE_PORT);
    this.proxy = HostConfig.of(LOCALHOST, DEFAULT_PROXY_PORT);
    this.threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
    this.proxyUser = null;
    this.proxyPass = null;
    this.serverName = DEFAULT_SERVER_NAME;
    this.userAgent = DEFAULT_USER_AGENT;
    this.bufferSize = DEFAULT_BUFFER_SIZE;
    this.directBuffer = true;
    this.kspath = null;
    this.pkpath = null;
    this.pubpath = null;
    this.kspass = null;
    this.cryptAlg = DEFAULT_CRYPT_ALGORITHM;
    this.timeout = DEFAULT_TIMEOUT;
  }


  public DoxyConfigBuilder(HostConfig host, HostConfig server, HostConfig remote, HostConfig proxy, String proxyUser, char[] proxyPass, Path privateKey, Path publicKey, Path keystore, char[] keystorePass, String serverName, String userAgent, String cryptAlg, int threadPoolSize, int bufferSize, boolean directBuffer, long timeout) {
    this.host = host;
    this.server = server;
    this.remote = remote;
    this.proxy = proxy;
    this.threadPoolSize = Runtime.getRuntime().availableProcessors() * 2;
    this.proxyUser = proxyUser;
    this.proxyPass = proxyPass;
    this.serverName = serverName;
    this.userAgent = userAgent;
    this.bufferSize = bufferSize;
    this.directBuffer = directBuffer;
    this.kspath = keystore;
    this.pkpath = privateKey;
    this.pubpath = publicKey;
    this.kspass = keystorePass;
    this.cryptAlg = cryptAlg;
    this.timeout = timeout;
  }
  
  
  public DoxyConfigBuilder host(String hostname, int port) {
    return new DoxyConfigBuilder(HostConfig.of(hostname, port), server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder host(HostConfig host) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder serverHost(String hostname, int port) {
    return new DoxyConfigBuilder(host, HostConfig.of(hostname, port), remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder serverHost(HostConfig server) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder remoteHost(String hostname, int port) {
    return new DoxyConfigBuilder(host, server, HostConfig.of(hostname, port), proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder remoteHost(HostConfig remote) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder proxyHost(String hostname, int port) {
    return new DoxyConfigBuilder(host, server, remote, HostConfig.of(hostname, port), proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder proxyHost(HostConfig proxy) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder threadPoolSize(int threadPoolSize) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder timeout(long timeout) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder bufferSize(int bufferSize) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder directBuffer(boolean directBuffer) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder proxyUser(String proxyUser) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder proxyPassword(char[] proxyPass) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder serverName(String serverName) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder userAgent(String userAgent) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder privateKeyPath(Path pkpath) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder publicKeyPath(Path pubpath) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder keystorePath(Path kspath) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder keystorePassword(char[] kspass) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public DoxyConfigBuilder cryptAlgorithm(String cryptAlg) {
    return new DoxyConfigBuilder(host, server, remote, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  public HostConfig getHost() {
    return host;
  }
  
  public HostConfig getServerHost() {
    return server;
  }
  
  public HostConfig getRemoteHost() {
    return remote;
  }
  
  public HostConfig getProxy() {
    return proxy;
  }
  
  public String getProxyUser() {
    return proxyUser;
  }
  
  public char[] getProxyPassword() {
    return proxyPass;
  }
  
  public int getBufferSize() {
    return bufferSize;
  }
  
  public int getThreadPoolSize() {
    return threadPoolSize;
  }
  
  public boolean isDirectBuffer() {
    return directBuffer;
  }
  
  public Path getPrivateKeyPath() {
    return pkpath;
  }
  
  public Path getPublicKeyPath() {
    return pubpath;
  }
  
  public Path getKeystorePath() {
    return kspath;
  }
  
  public char[] getKeystorePassword() {
    return kspass;
  }
  
  public String getServerName() {
    return serverName;
  }
  
  public String getUserAgent() {
    return userAgent;
  }
  
  public String getCryptAlgorithm() {
    return userAgent;
  }
  
  public DoxyConfigBuilder loadProperties(Properties prop) {
    TypedProperties props = new TypedProperties(prop);
    return bufferSize(props.getAsInt(PROP_BUFFER_SIZE, DEFAULT_BUFFER_SIZE))
        .cryptAlgorithm(props.getProperty(PROP_SECURITY_CRYPT_ALGORITHM, DEFAULT_CRYPT_ALGORITHM))
        .directBuffer(props.getAsBoolean(PROP_BUFFER_DIRECT))
        .host(props.getAsHost(PROP_HOST, HostConfig.of(LOCALHOST, DEFAULT_HOST_PORT)))
        .keystorePath(props.getAsPath(PROP_SECURITY_KEYSTORE_PATH))
        .keystorePassword(props.getAsChars(PROP_SECURITY_KEYSTORE_PASS))
        .privateKeyPath(props.getAsPath(PROP_SECURITY_PRIVATEKEY_PATH))
        .publicKeyPath(props.getAsPath(PROP_SECURITY_PUBLICKEY_PATH))
        .proxyHost(props.getAsHost(PROP_PROXY_HOST, HostConfig.of(LOCALHOST, DEFAULT_PROXY_PORT)))
        .proxyUser(props.getProperty(PROP_PROXY_USER))
        .proxyPassword(props.getAsChars(PROP_PROXY_PASS))
        .remoteHost(props.getAsHost(PROP_REMOTE_HOST, HostConfig.of(LOCALHOST, DEFAULT_REMOTE_PORT)))
        .serverHost(props.getAsHost(PROP_SERVER_HOST, HostConfig.of(LOCALHOST, DEFAULT_SERVER_PORT)))
        .serverName(props.getProperty(PROP_SERVER_NAME, DEFAULT_SERVER_NAME))
        .threadPoolSize(props.getAsInt(PROP_THREADPOOL_SIZE))
        .timeout(props.getAsLong(PROP_SERVER_TIMEOUT, DEFAULT_TIMEOUT))
        .userAgent(props.getProperty(PROP_USERAGENT, DEFAULT_USER_AGENT));
  }
  
  public DoxyConfigBuilder loadProperties(Path propsPath) throws IOException {
    try (BufferedReader rdr = Files.newBufferedReader(propsPath, StandardCharsets.UTF_8)) {
      Properties props = new Properties();
      props.load(rdr);
      return loadProperties(props);
    }
  }
  
  public DoxyConfigBuilder loadEnvironment() {
    Properties props = new Properties();
    Optional<String> val = Optional.ofNullable(System.getenv(ENV_BUFFER_DIRECT));
    val.ifPresent(v->props.setProperty(PROP_BUFFER_DIRECT, v));
    val = Optional.ofNullable(System.getenv(ENV_BUFFER_SIZE));
    val.ifPresent(v->props.setProperty(PROP_BUFFER_SIZE, v));
    val = Optional.ofNullable(System.getenv(ENV_HOST));
    val.ifPresent(v->props.setProperty(PROP_HOST, v));
    val = Optional.ofNullable(System.getenv(ENV_PROXY_HOST));
    val.ifPresent(v->props.setProperty(PROP_PROXY_HOST, v));
    val = Optional.ofNullable(System.getenv(ENV_PROXY_PASS));
    val.ifPresent(v->props.setProperty(PROP_PROXY_PASS, v));
    val = Optional.ofNullable(System.getenv(ENV_PROXY_USER));
    val.ifPresent(v->props.setProperty(PROP_PROXY_USER, v));
    val = Optional.ofNullable(System.getenv(ENV_REMOTE_HOST));
    val.ifPresent(v->props.setProperty(PROP_REMOTE_HOST, v));
    val = Optional.ofNullable(System.getenv(ENV_SECURITY_CRYPT_ALGORITHM));
    val.ifPresent(v->props.setProperty(PROP_SECURITY_CRYPT_ALGORITHM, v));
    val = Optional.ofNullable(System.getenv(ENV_SECURITY_KEYSTORE_PASS));
    val.ifPresent(v->props.setProperty(PROP_SECURITY_KEYSTORE_PASS, v));
    val = Optional.ofNullable(System.getenv(ENV_SECURITY_KEYSTORE_PATH));
    val.ifPresent(v->props.setProperty(PROP_SECURITY_KEYSTORE_PATH, v));
    val = Optional.ofNullable(System.getenv(ENV_SECURITY_PRIVATEKEY_PATH));
    val.ifPresent(v->props.setProperty(PROP_SECURITY_PRIVATEKEY_PATH, v));
    val = Optional.ofNullable(System.getenv(ENV_SECURITY_PUBLICKEY_PATH));
    val.ifPresent(v->props.setProperty(PROP_SECURITY_PUBLICKEY_PATH, v));
    val = Optional.ofNullable(System.getenv(ENV_SERVER_HOST));
    val.ifPresent(v->props.setProperty(PROP_SERVER_HOST, v));
    val = Optional.ofNullable(System.getenv(ENV_SERVER_NAME));
    val.ifPresent(v->props.setProperty(PROP_SERVER_NAME, v));
    val = Optional.ofNullable(System.getenv(ENV_SERVER_TIMEOUT));
    val.ifPresent(v->props.setProperty(PROP_SERVER_TIMEOUT, v));
    val = Optional.ofNullable(System.getenv(ENV_THREADPOOL_SIZE));
    val.ifPresent(v->props.setProperty(PROP_THREADPOOL_SIZE, v));
    val = Optional.ofNullable(System.getenv(ENV_USERAGENT));
    val.ifPresent(v->props.setProperty(PROP_USERAGENT, v));
    return loadProperties(props);
  }
  
  public DoxyConfig build() {
    Objects.requireNonNull(host, "Bad null Host");
    Objects.requireNonNull(server, "Bad null Server Host");
    Objects.requireNonNull(cryptAlg, "Bad null cryptography algorithm");
    if(threadPoolSize <= 0) throw new IllegalStateException("Bad thread pool size: " + threadPoolSize);
    if(bufferSize <= 0) throw new IllegalStateException("Bad buffer size: " + bufferSize);
    return DoxyConfig.of(
        host, 
        server, 
        remote, 
        ProxyConfig.of(proxy, proxyUser, proxyPass),
        SecurityConfig.of(pkpath, pubpath, kspath, kspass, cryptAlg),
        serverName,
        userAgent,
        threadPoolSize, 
        bufferSize, 
        directBuffer,
        timeout <= 0 ? DEFAULT_TIMEOUT : timeout
    );
  }
  
  
  
  public static DoxyConfigBuilder newBuilder() {
    return new DoxyConfigBuilder();
  }
  
  
  
  public static final String PROP_HOST = "host";
  public static final String PROP_USERAGENT = "useragent";
  public static final String PROP_SERVER_NAME = "server.name";
  public static final String PROP_SERVER_HOST = "server.host";
  public static final String PROP_SERVER_TIMEOUT = "server.timeout";
  public static final String PROP_REMOTE_HOST = "remote.host";
  public static final String PROP_PROXY_HOST = "proxy.host";
  public static final String PROP_PROXY_USER = "proxy.user";
  public static final String PROP_PROXY_PASS = "proxy.pass";
  public static final String PROP_SECURITY_KEYSTORE_PATH = "security.keystore.path";
  public static final String PROP_SECURITY_KEYSTORE_PASS = "security.keystore.pass";
  public static final String PROP_SECURITY_PUBLICKEY_PATH = "security.publickey.path";
  public static final String PROP_SECURITY_PRIVATEKEY_PATH = "security.privatekey.path";
  public static final String PROP_SECURITY_CRYPT_ALGORITHM = "security.crypt.algorithm";
  public static final String PROP_BUFFER_SIZE = "buffer.size";
  public static final String PROP_BUFFER_DIRECT = "buffer.direct";
  public static final String PROP_THREADPOOL_SIZE = "threadpool.size";
  
  public static final String ENV_HOST = "DOXY_HOST";
  public static final String ENV_USERAGENT = "DOXY_USERAGENT";
  public static final String ENV_SERVER_NAME = "DOXY_SERVER_NAME";
  public static final String ENV_SERVER_HOST = "DOXY_SERVER_HOST";
  public static final String ENV_SERVER_TIMEOUT = "DOXY_SERVER_TIMEOUT";
  public static final String ENV_REMOTE_HOST = "DOXY_REMOTE_HOST";
  public static final String ENV_PROXY_HOST = "DOXY_PROXY_HOST";
  public static final String ENV_PROXY_USER = "DOXY_PROXY_USER";
  public static final String ENV_PROXY_PASS = "DOXY_PROXY_PASS";
  public static final String ENV_SECURITY_KEYSTORE_PATH = "DOXY_SECURITY_KEYSTORE_PATH";
  public static final String ENV_SECURITY_KEYSTORE_PASS = "DOXY_SECURITY_KEYSTORE_PASS";
  public static final String ENV_SECURITY_PUBLICKEY_PATH = "DOXY_SECURITY_PUBLICKEY_PATH";
  public static final String ENV_SECURITY_PRIVATEKEY_PATH = "DOXY_SECURITY_PRIVATEKEY_PATH";
  public static final String ENV_SECURITY_CRYPT_ALGORITHM = "DOXY_SECURITY_CRYPT_ALGORITHM";
  public static final String ENV_BUFFER_SIZE = "DOXY_BUFFER_SIZE";
  public static final String ENV_BUFFER_DIRECT = "DOXY_BUFFER_DIRECT";
  public static final String ENV_THREADPOOL_SIZE = "DOXY_THREAD_POOL_SIZE";
  
}
