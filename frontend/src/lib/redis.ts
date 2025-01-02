import { Redis } from 'ioredis';


import Redlock from 'redlock'
import * as process from 'node:process';



const redis = new Redis({
  host: process.env.REDIS_HOST || "127.0.0.1",
  port: parseInt(process.env.REDIS_PORT),
  password: process.env.REDIS_PASSWORD || undefined,
  db: 0,
});

redis.on('error', (err)=>{
  console.log("Redis Error", err);

});
const redlock = new Redlock([redis], {
  retryCount : 10,
  retryDelay : 200,
  retryJitter: 200,
})

redlock.on('error', (err) =>{
  console.error("Redlcok Error", err);
});


// async function connectRedis() {
//   if (!redis.isOpen) {
//     await redis.connect();
//     console.log("Connected to Redis.");
//   }
// }

export { redis, redlock };
