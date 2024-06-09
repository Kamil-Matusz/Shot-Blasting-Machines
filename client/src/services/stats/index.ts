import httpClient from "../httpClient";
import type  Stats from '@/models/stats';

const base = 'stats'

async function getStats() {
  return await httpClient.get<Stats>(base);
}
async function getSparkline() {
  return await httpClient.get<Array<number>>(`${base}/sparkline`);
}


export default {
  getStats,
  getSparkline,
};
