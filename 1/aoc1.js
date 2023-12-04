const { open } = require('node:fs/promises');

(async () => {
  const file = await open('./input.txt');
  const strMap = {one:1,two:2,three:3,four:4,five:5,six:6,seven:7,eight:8,nine:9}
  const revStrMap = {eno:1,owt:2,eerht:3,ruof:4,evif:5,xis:6,neves:7,thgie:8,enin:9}
  
  const mapDigit = (i, rev) => {
    if(i.length === 1) {
      return i
    }

    return rev ? revStrMap[i] : strMap[i]
  }

  let acc = 0
  for await (const line of file.readLines()) {
    console.log(acc)

    m = line.match(/\d|one|two|three|four|five|six|seven|eight|nine/)
    first = mapDigit(m[0], false)

    rline = line.split('').reverse().join('')
    rm = rline.match(/\d|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin/)
    last = mapDigit(rm[0], true)
    
    n = parseInt(`${first}${last}`, 10)
    
    acc += n
  }

  console.log(acc)
})();
