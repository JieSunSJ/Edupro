const fs = require('fs');
const p = 'E:/学习/system-edupro/edupro/edupro.sql';
const lines = fs.readFileSync(p, 'utf8').split(/\r?\n/);
lines.slice(0, 50).forEach((l, i) => console.log(`${i+1}: ${l}`));
console.log('... total lines:', lines.length);