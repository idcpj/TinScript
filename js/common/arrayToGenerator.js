function * arrayToGenerator(arr) {
    for( let i=0;i<arr.length;i++){
        yield arr[i]
    }
}


function test(){
    let generator = arrayToGenerator([1,2,2]);

    console.log(generator.next())
    console.log(generator.next())
    console.log(generator.next())
    console.log(generator.next())

}

// test()
//{ value: 1, done: false }
// { value: 2, done: false }
// { value: 2, done: false }
// { value: undefined, done: true }

module.exports= arrayToGenerator