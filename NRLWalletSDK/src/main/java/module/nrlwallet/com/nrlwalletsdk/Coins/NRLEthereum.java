package module.nrlwallet.com.nrlwalletsdk.Coins;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;

import java.io.IOException;

import io.github.novacrypto.bip32.ExtendedPrivateKey;
import io.github.novacrypto.bip32.ExtendedPublicKey;
import io.github.novacrypto.bip32.Network;
import io.github.novacrypto.bip44.Account;
import io.github.novacrypto.bip44.AddressIndex;
import io.github.novacrypto.bip44.BIP44;
import module.nrlwallet.com.nrlwalletsdk.Network.CoinType;
import module.nrlwallet.com.nrlwalletsdk.Network.Ethereum;
import module.nrlwallet.com.nrlwalletsdk.Utils.ExtendedPrivateKeyBIP32;
import module.nrlwallet.com.nrlwalletsdk.Utils.HTTPRequest;
import module.nrlwallet.core.BRCoreChainParams;
import module.nrlwallet.core.BRCoreMasterPubKey;
import module.nrlwallet.core.BRCoreWallet;
import module.nrlwallet.core.BRCoreWalletManager;
import module.nrlwallet.core.ethereum.BREthereumLightNode;
import module.nrlwallet.core.ethereum.BREthereumWallet;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NRLEthereum extends NRLCoin {
    Network network = Ethereum.MAIN_NET;
    int coinType = 60;
    String seedKey = "Bitcoin seed";
    String curve = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141";
    byte[] bSeed;
    String rootKey;
    AddressIndex addressIndex;
    String extendedPrivateKey;
    String extendedPublicKey;
    String walletAddress;
    String balance;
    BREthereumLightNode.JSON_RPC node;

    public NRLEthereum(byte[] seed) {
        super(seed, Ethereum.MAIN_NET, 60, "Bitcoin seed", "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141");
        bSeed = seed;
        this.init();
    }

    private void init1() {
//        Web3j web3j = Web3jFactory.build()
    }
    private void createWallet() {

    }

    private void init() {
        ExtendedPrivateKey root = ExtendedPrivateKey.fromSeed(bSeed, network);
        addressIndex = BIP44.m()
                .purpose44()
                .coinType(coinType)
                .account(0)
                .external()
                .address(0);

        this.rootKey = new ExtendedPrivateKeyBIP32().getRootKey(bSeed, CoinType.ETHEREUM);
        ExtendedPrivateKey privateKey;
        privateKey = ExtendedPrivateKey.fromSeed(bSeed, Ethereum.MAIN_NET);
        ExtendedPrivateKey child = privateKey.derive(addressIndex, AddressIndex.DERIVATION);
        ExtendedPublicKey childPub = child.neuter();
        extendedPrivateKey = child.extendedBase58();   //Extended Private Key
        extendedPublicKey = childPub.extendedBase58();    //Extended Public Key
        walletAddress = childPub.p2pkhAddress();
        String str4 = childPub.p2shAddress();
    }

    @Override
    public String getAddress() {
        return this.walletAddress;
    }

    @Override
    public String getPrivateKey() {
        return this.extendedPrivateKey;
    }

    public String getRootKey() {
        return this.rootKey;
    }
    public void getBalance() {
        String url_getbalance = "/api/v1/balance/" + this.walletAddress;
        new HTTPRequest().run(url_getbalance, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    // Do what you want to do with the response.
                } else {
                    // Request not successful
                }
            }
        });
    }
    public void getTransactions() {

    }
    public void createTransaction(long amount, String address, String memo, long fee) {

    }

}
