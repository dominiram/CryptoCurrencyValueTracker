package app.naum.myapplication.utils

import app.naum.myapplication.database.CryptoDatabase
import app.naum.myapplication.database.CryptoDatabaseModel
import app.naum.myapplication.network.models.CryptoModel
import javax.inject.Inject

class NetworkToDatabaseCryptoModelMapper
@Inject constructor() {
    fun mapToDatabaseModel(networkModel: CryptoModel): CryptoDatabaseModel {
        return CryptoDatabaseModel(
            networkModel.id,
            networkModel.imageUrl,
            networkModel.symbol,
            networkModel.name,
            networkModel.description,
            networkModel.sortOrder,
            networkModel.isTrading,
            networkModel.totalMined,
            networkModel.coinWebsite,
            networkModel.blockReward,
            networkModel.blockTime
        )
    }

    fun mapToNetworkAndApplicationModel(databaseModel: CryptoDatabaseModel): CryptoModel {
        return CryptoModel(
            databaseModel.id,
            databaseModel.imageUrl,
            databaseModel.symbol,
            databaseModel.name,
            databaseModel.description,
            databaseModel.sortOrder,
            databaseModel.isTrading,
            databaseModel.totalMined,
            databaseModel.coinWebsite,
            databaseModel.blockReward,
            databaseModel.blockTime
        )
    }
}