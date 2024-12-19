"use client"
import * as React from "react"

import {Card, CardContent} from "@/components/ui/card"
import {Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious,} from "@/components/ui/carousel"
import Autoplay from "embla-carousel-autoplay";
// import {ScrollArea} from "@/components/ui/scroll-area"
// import {Separator} from "@/components/ui/separator"
//
// const tags = Array.from({length: 50}).map(
//     (_, i, a) => `v1.2.0-beta.${a.length - i}`
// )


export function Ads() {
    return (
        <div className="">
            <Carousel plugins={[
                Autoplay({
                    delay: 4000, // 2 seconds delay between slides
                }),
            ]} className="flex flex-row w-full h-full max-h-[50vh] max-w-6xl mt-12 ml-20">
                <CarouselContent>
                    {Array.from({length: 5}).map((_, index) => (
                        <CarouselItem key={index}>
                            <div className="p-1">
                                <Card className="bg-brand-mid rounded-3xl">
                                    <CardContent className="flex items-center justify-center h-[50vh] p-6">
                                        {/*<span className="text-4xl font-semibold">{index + 1}</span>*/}
                                        <img
                                            src={`ad${index + 1}.png`}
                                            alt={`public/AdsImage/ad ${index + 1}`}
                                            className="h-full w-full object-cover rounded-2xl"
                                        />
                                    </CardContent>
                                </Card>
                            </div>
                        </CarouselItem>
                    ))}
                </CarouselContent>
                <CarouselPrevious/>
                <CarouselNext/>
            </Carousel>


            {/*<ScrollArea className="h-72 w-48 rounded-md border">*/}
            {/*    <div className="p-4">*/}
            {/*        <h4 className="mb-4 text-sm font-medium leading-none">Tags</h4>*/}
            {/*        {tags.map((tag) => (*/}
            {/*            <>*/}
            {/*                <div key={tag} className="text-sm">*/}
            {/*                    {tag}*/}
            {/*                </div>*/}
            {/*                <Separator className="my-2"/>*/}
            {/*            </>*/}
            {/*        ))}*/}
            {/*    </div>*/}
            {/*</ScrollArea>*/}
        </div>

    )
}