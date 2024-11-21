conn = new Mongo();
db = conn.getDB("market")
db.createUser(
    {
        user: "arturk",
        pwd: "arturk2308",
        roles: [
            {
                role: "readWrite",
                db: "market"
            }
        ]
    }
);
db.createCollection("orders");