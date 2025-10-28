package org.hynaf.Core.Node.NodeID;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NodeIDFactory {
    private static final Pattern LOCAL_PATTERN = Pattern.compile("^local://([a-zA-Z0-9_-])$");
    private static final Pattern PROCESS_PATTERN = Pattern.compile("^process://localhost:(\\d+)/([a-zA-Z0-9_-]+)$");
    private static final Pattern NETWORK_PATTERN = Pattern.compile("^network://([^:]+):(\\d+)/([a-zA-Z0-9_-]+)$");
    private static final Pattern REMOTE_PATTERN = Pattern.compile("^([a-zA-Z][a-zA-Z0-9]+)://(.+)/([a-zA-Z0-9_-]+)$");

    private NodeIDFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static AbstractNodeID parse(String stringID){
        if(stringID == null || stringID.trim().isEmpty()) {
            throw new IllegalArgumentException("Node Identifier cannot be null or empty");
        }
        stringID = stringID.trim();
        Matcher localMatcher = LOCAL_PATTERN.matcher(stringID);
        if(localMatcher.matches()) {
            String nodeID = localMatcher.group(1);
            return new LocalNodeID(nodeID);
        }
        Matcher processMatcher = PROCESS_PATTERN.matcher(stringID);
        if(processMatcher.matches()) {
            int port = Integer.parseInt(processMatcher.group(1));
            String identifier = processMatcher.group(2);
            return new ProcessNodeID(identifier, port);
        }
        Matcher networkMatcher = NETWORK_PATTERN.matcher(stringID);
        if(networkMatcher.matches()) {
            String host = networkMatcher.group(1);
            int port = Integer.parseInt(networkMatcher.group(2));
            String identifier = networkMatcher.group(3);
            return new NetworkNodeID(identifier, host, port);
        }
        Matcher remoteMatcher = REMOTE_PATTERN.matcher(stringID);
        if(remoteMatcher.matches()) {
            String protocol = remoteMatcher.group(1);
            String endpoint = remoteMatcher.group(2);
            String identifier = remoteMatcher.group(3);
            if(protocol.equals("local") || protocol.equals("process") || protocol.equals("network")) {
                throw new IllegalArgumentException("Malformed remote protocol, NodeID : " + stringID);
            }
            return new RemoteNodeID(identifier, endpoint, protocol);
        }
        throw new IllegalArgumentException("Invalid NodeID format: " + stringID +
                "\nSupported formats:" +
                "\n  - local://nodeIdentifier" +
                "\n  - process://localhost:port/nodeIdentifier" +
                "\n  - network://host:port/nodeIdentifier" +
                "\n  - protocol://endpoint/nodeIdentifier");
    }
    public static boolean isValid(String stringID) {
        if(stringID == null || stringID.trim().isEmpty()) {
            return false;
        }
        try {
            parse(stringID);
            return true;
        }catch(Exception e) {
           return false;
        }
    }

    public static AbstractNodeID fromComponents(String stringID, String protocol, String host, Integer port) {
        String concat = protocol + "://" + host + ((port == null) ? "" : ":" + port) + "/" + port;
        return parse(concat);
    }
}
