import * as React from "react"
import {Card, CardContent} from "@/components/ui/card"
import {Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious,} from "@/components/ui/carousel"

export function Category() {
    return (
        <div className="bg-brand-mid pl-40 pr-40 pt-10 pb-16">
            <h2 className="flex justify-center text-4xl font-sans text-black">PRODUCT CATEGORY</h2>
            <Carousel
                opts={{
                    align: "start",
                }}
                className="w-full pt-10"
            >
                <CarouselContent className="max-w-3xl">
                    {Array.from({length: 15}).map((_, index) => (
                        <CarouselItem key={index} className="md:basis-1/2 lg:basis-1/3">
                            <div className="p-1">
                                <Card className="w-[230px] h-[230px] rounded-full">
                                    <CardContent className="flex items-center justify-center p-1">
                                        <img
                                            src={`demo1.png`}
                                            alt={`alt text ${1}`}
                                            className="h-full w-full object-cover rounded-full"
                                        />
                                    </CardContent>
                                </Card>
                                <div className="flex flex-row w-full pt-2 justify-center">
                                    {/*<div className="flex flex-col justify-center w-3/4">*/}
                                    <h2 className="justify-center text-xl font-sans font-bold text-black">CategoryName</h2>
                                    {/*</div>*/}
                                </div>

                            </div>
                        </CarouselItem>
                    ))}
                </CarouselContent>
                <CarouselPrevious/>
                <CarouselNext/>

            </Carousel>
        </div>
    )
}